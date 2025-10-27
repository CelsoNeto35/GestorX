package br.com.gestorx.api.service;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class RelatorioService {

    @Autowired
    private DataSource dataSource;

    public byte[] gerarRelatorioVendaPDF(Map<String, Object> parametros) throws Exception {
        Connection conexao = null;
        
        try {
            System.out.println("=== INICIANDO GERAÇÃO DO RELATÓRIO (SERVICE) ===");
            
            // 1. Obter conexão com o banco
            conexao = dataSource.getConnection();
            System.out.println("Conexão obtida com sucesso");
            
            // 2. Carregar o arquivo .jasper PRÉ-COMPILADO
            InputStream jasperStream = new ClassPathResource("Relatorios/RelatorioVenda.jasper").getInputStream();
            System.out.println("Arquivo jasper carregado");
            
            // 3. Carregar o relatório compilado
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
            System.out.println("Relatório carregado com sucesso");
            
            System.out.println("Parâmetros recebidos: " + parametros);
            
            // 4. Preencher o relatório
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conexao);
            System.out.println("Relatório preenchido");
            
            // 5. Exportar para PDF
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            System.out.println("PDF gerado com sucesso. Tamanho: " + pdfBytes.length + " bytes");
            
            return pdfBytes;
            
        } catch (Exception e) {
            System.err.println("=== ERRO AO GERAR RELATÓRIO (SERVICE) ===");
            e.printStackTrace();
            throw e;
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