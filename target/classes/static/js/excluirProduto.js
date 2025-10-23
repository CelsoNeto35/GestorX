// Adiciona ouvintes aos botões de exclusão
document.querySelectorAll('.excluir').forEach(function(button) {
    button.addEventListener('click', function() {
        if (confirm('Confirma a exclusão?')) {

            const row = this.closest('tr'); // linha da tabela
            const produtoId = this.dataset.produtoId;

            // Chamada AJAX para excluir o produto
            fetch(`/produto/${produtoId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => {
                if (response.ok) {
                    console.log('Produto excluído com sucesso.');
                    row.remove(); // remove a linha da tabela
                } else {
                    console.error('Erro ao excluir produto.');
                    alert('Erro ao excluir produto');
                }
            })
            .catch(error => {
                console.error('Erro de rede:', error);
                alert('Erro de rede: ' + error);
            });
        }
    });
});
