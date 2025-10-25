package br.com.gestorx.api.service;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

@Service
public class RelatorioService {

    @Autowired
    private DataSource dataSource;

    public byte[] gerarRelatorioVendaPDF(Map<String, Object> parametros) throws Exception {
        Connection conexao = null;
        
        try {
            conexao = dataSource.getConnection();
            
            // CAMINHO CORRIGIDO: Relatorios com R maiúsculo
            InputStream jasperStream = new ClassPathResource("Relatorios/RelatorioVenda.jasper").getInputStream();
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, parametros, conexao);
            
            return JasperExportManager.exportReportToPdf(jasperPrint);
            
        } finally {
            if (conexao != null) {
                conexao.close();
            }
        }
    }
}