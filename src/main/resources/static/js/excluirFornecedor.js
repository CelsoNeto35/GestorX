
document.querySelectorAll('.excluir').forEach(function(button) {
    button.addEventListener('click', 
    function() {
        if (confirm('Confirma a exclusão?')) {

            const row = this.closest('tr'); // Obtém a linha atual da tabela

            const fornecedorId = this.dataset.fornecedorId;
            
            // Realize a chamada AJAX para excluir o recurso
            fetch(`/fornecedor/${fornecedorId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => {
                if (response.ok) {
                    // A exclusão foi bem-sucedida
                    console.log('Fornecedor excluído com sucesso.');

                    // Remove a linha da tabela após a exclusão
                    row.remove();
                } else {
                    // A solicitação DELETE falhou
                    console.error('Erro ao excluir fornecedor.');
                    alert('Erro ao excluir fornecedor.');
                }
            })
            .catch(error => {
                // Lidar com erros de rede ou outros erros
                console.error('Erro de rede:', error);
                alert('Erro de rede:' + error);
            });
        }
    });
});
