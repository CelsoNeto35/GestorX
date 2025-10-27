package br.com.gestorx.api.controller;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/vendas/pdf")
    public ResponseEntity<byte[]> downloadRelatorioVendaPDF(
            @RequestParam(required = false) BigDecimal preco_total,
            @RequestParam(required = false) BigDecimal comissao_percentual,
            @RequestParam(required = false) String nome_completo_razao_social,
            @RequestParam(required = false) String cnpj_cpf,
            @RequestParam(required = false) String produto_descricao) {
        
        Connection conexao = null;
        
        try {
            System.out.println("=== INICIANDO GERAÇÃO DO RELATÓRIO ===");
            
            // 1. Obter conexão com o banco
            conexao = dataSource.getConnection();
            System.out.println("Conexão obtida com sucesso");
            
            // 2. Carregar e compilar o arquivo .jrxml (TEMPORÁRIO até recompilar o .jasper)
            InputStream jrxmlStream = new ClassPathResource("Relatorios/RelatorioVenda.jrxml").getInputStream();
            System.out.println("Arquivo jrxml carregado");
            
            // 3. Compilar o relatório (ignorando atributos uuid)
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            System.out.println("Relatório compilado com sucesso");
            
            // 4. Montar parâmetros do relatório
            Map<String, Object> parametros = new HashMap<>();
            
            if (preco_total != null) {
                parametros.put("preco_total", preco_total);
            }
            if (comissao_percentual != null) {
                parametros.put("comissao_percentual", comissao_percentual);
            }
            if (nome_completo_razao_social != null) {
                parametros.put("nome_completo_razao_social", nome_completo_razao_social);
            }
            if (cnpj_cpf != null) {
                parametros.put("cnpj_cpf", cnpj_cpf);
            }
            if (produto_descricao != null) {
                parametros.put("produto_descricao", produto_descricao);
            }
            
            System.out.println("Parâmetros configurados: " + parametros);
            
            // 5. Preencher o relatório
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conexao);
            System.out.println("Relatório preenchido");
            
            // 6. Exportar para PDF
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            System.out.println("PDF gerado com sucesso. Tamanho: " + pdfBytes.length + " bytes");
            
            // 7. Configurar headers para download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "RelatorioVenda.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
                    
        } catch (Exception e) {
            System.err.println("=== ERRO AO GERAR RELATÓRIO ===");
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                    System.out.println("Conexão fechada");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}