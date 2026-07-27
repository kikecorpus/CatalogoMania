package CatagoloEmprende.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Motor de plantillas independiente, usado SOLO para generar el HTML
 * que luego se convierte a PDF.
 *
 * Nota: usamos TemplateMode.HTML (no existe XHTML en Thymeleaf 3.x,
 * ese modo era de Thymeleaf 2.x). Lo que sí debemos cuidar es que la
 * plantilla catalogo.html tenga sus etiquetas bien cerradas
 * (ej. <img ... /> en vez de <img ...>), porque eso es lo que
 * realmente exige Flying Saucer al recibir el HTML final.
 */
@Configuration
public class PdfTemplateConfig {

    @Bean
    public TemplateEngine pdfTemplateEngine() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/pdf/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        return templateEngine;
    }
}