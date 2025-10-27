// busca-cep.js - Script para buscar endereço automaticamente via CEP
// API: https://cep.awesomeapi.com.br

document.addEventListener('DOMContentLoaded', function() {
    const cepInput = document.getElementById('cep');
    const ruaInput = document.getElementById('ruaLogradouro');
    const bairroInput = document.getElementById('bairro');
    const cidadeInput = document.getElementById('cidade');
    const estadoInput = document.getElementById('estado');

    if (!cepInput) {
        console.warn('Campo CEP não encontrado');
        return;
    }

    // Função para buscar o CEP na API
    async function buscarCEP(cep) {
        const cepLimpo = cep.replace(/\D/g, '');

        if (cepLimpo.length !== 8) {
            return;
        }

        // Indicador visual de carregamento
        cepInput.style.borderBottomColor = '#ffc107';
        cepInput.style.borderBottomWidth = '3px';

        // Desabilita os campos enquanto carrega
        desabilitarCampos(true);

        try {
            const response = await fetch(`https://cep.awesomeapi.com.br/json/${cepLimpo}`);

            if (!response.ok) {
                throw new Error('CEP não encontrado');
            }

            const data = await response.json();

            // Preenche os campos com os dados retornados
            if (ruaInput) ruaInput.value = data.address || '';
            if (bairroInput) bairroInput.value = data.district || '';
            if (cidadeInput) cidadeInput.value = data.city || '';
            if (estadoInput) estadoInput.value = data.state || '';

            // Feedback visual de sucesso
            cepInput.style.borderBottomColor = '#28a745';
            mostrarMensagem('CEP encontrado com sucesso!', 'success');

            // Foca no campo número após preencher
            const numeroInput = document.getElementById('numero');
            if (numeroInput) {
                numeroInput.focus();
            }

        } catch (error) {
            console.error('Erro ao buscar CEP:', error);
            cepInput.style.borderBottomColor = '#dc3545';
            mostrarMensagem('CEP não encontrado. Verifique o número digitado.', 'error');
            
            // Limpa os campos em caso de erro
            limparCamposEndereco();
        } finally {
            // Reabilita os campos
            desabilitarCampos(false);

            // Remove o indicador visual após 2 segundos
            setTimeout(() => {
                cepInput.style.borderBottomColor = '#e0e0e0';
                cepInput.style.borderBottomWidth = '2px';
            }, 2000);
        }
    }

    // Função para desabilitar/habilitar campos de endereço
    function desabilitarCampos(desabilitar) {
        if (ruaInput) ruaInput.disabled = desabilitar;
        if (bairroInput) bairroInput.disabled = desabilitar;
        if (cidadeInput) cidadeInput.disabled = desabilitar;
        if (estadoInput) estadoInput.disabled = desabilitar;
    }

    // Função para limpar campos de endereço
    function limparCamposEndereco() {
        if (ruaInput) ruaInput.value = '';
        if (bairroInput) bairroInput.value = '';
        if (cidadeInput) cidadeInput.value = '';
        if (estadoInput) estadoInput.value = '';
    }

    // Função para mostrar mensagem de feedback
    function mostrarMensagem(mensagem, tipo) {
        // Remove mensagem anterior se existir
        const mensagemExistente = document.getElementById('cep-feedback-msg');
        if (mensagemExistente) {
            mensagemExistente.remove();
        }

        // Cria nova mensagem
        const feedbackDiv = document.createElement('div');
        feedbackDiv.id = 'cep-feedback-msg';
        feedbackDiv.style.cssText = `
            font-size: 0.8rem;
            margin-top: 5px;
            font-weight: 500;
            transition: opacity 0.3s ease;
        `;

        if (tipo === 'success') {
            feedbackDiv.style.color = '#28a745';
            feedbackDiv.innerHTML = `<i class="bi bi-check-circle"></i> ${mensagem}`;
        } else {
            feedbackDiv.style.color = '#dc3545';
            feedbackDiv.innerHTML = `<i class="bi bi-exclamation-circle"></i> ${mensagem}`;
        }

        // Insere a mensagem após o campo CEP
        cepInput.parentElement.appendChild(feedbackDiv);

        // Remove a mensagem após 5 segundos
        setTimeout(() => {
            feedbackDiv.style.opacity = '0';
            setTimeout(() => feedbackDiv.remove(), 300);
        }, 5000);
    }

    // Formatação de CEP
    cepInput.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        value = value.replace(/(\d{5})(\d)/, '$1-$2');
        e.target.value = value;
    });

    // Busca automática ao sair do campo (blur)
    cepInput.addEventListener('blur', function() {
        const cep = this.value;
        if (cep.replace(/\D/g, '').length === 8) {
            buscarCEP(cep);
        }
    });

    // Busca ao pressionar Enter
    cepInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            const cep = this.value;
            if (cep.replace(/\D/g, '').length === 8) {
                buscarCEP(cep);
            }
        }
    });

    // Opcional: Adicionar botão de busca ao lado do campo CEP
    adicionarBotaoBusca();

    function adicionarBotaoBusca() {
        const colCep = cepInput.closest('.col-md-3');
        if (!colCep) return;

        // Verifica se já existe um botão
        if (document.getElementById('btn-buscar-cep')) return;

        // Cria o botão
        const btnBuscar = document.createElement('button');
        btnBuscar.id = 'btn-buscar-cep';
        btnBuscar.type = 'button';
        btnBuscar.className = 'btn btn-sm mt-2';
        btnBuscar.style.cssText = `
            border: 1px solid #000;
            background: transparent;
            color: #000;
            font-size: 0.85rem;
            font-weight: 600;
            padding: 5px 15px;
            transition: all 0.3s ease;
        `;
        btnBuscar.innerHTML = '<i class="bi bi-search"></i> Buscar CEP';

        // Hover effect
        btnBuscar.addEventListener('mouseenter', function() {
            this.style.background = '#000';
            this.style.color = '#fff';
        });

        btnBuscar.addEventListener('mouseleave', function() {
            this.style.background = 'transparent';
            this.style.color = '#000';
        });

        // Ação do botão
        btnBuscar.addEventListener('click', function() {
            const cep = cepInput.value;
            if (cep.replace(/\D/g, '').length === 8) {
                buscarCEP(cep);
            } else {
                mostrarMensagem('Digite um CEP válido com 8 dígitos', 'error');
                cepInput.focus();
            }
        });

        // Adiciona o botão após o input
        cepInput.parentElement.appendChild(btnBuscar);
    }
});