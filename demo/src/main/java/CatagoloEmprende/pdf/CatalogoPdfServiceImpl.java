package CatagoloEmprende.pdf;

import CatagoloEmprende.dto.CatalogoDTO;
import CatagoloEmprende.dto.ProductoDTO;
import CatagoloEmprende.service.CatalogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateInputException;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CatalogoPdfServiceImpl implements CatalogoPdfService {

    private static final String PLANTILLA_POR_DEFECTO = "catalogo";

    private final CatalogoService catalogoService;

    @Qualifier("pdfTemplateEngine")
    private final TemplateEngine pdfTemplateEngine;

    @Override
    public byte[] generarPdf(Long catalogoId) {
        CatalogoDTO.Response catalogo = catalogoService.buscarPorId(catalogoId);
        String html = renderizarHtml(catalogo);
        return convertirHtmlAPdf(html);
    }

    private String renderizarHtml(CatalogoDTO.Response catalogo) {
        Context context = new Context();

        context.setVariable("nombreCatalogo", catalogo.nombreCatalogo());
        context.setVariable("colorPrincipal", valorOrDefault(catalogo.personalizacion().colorPrincipal(), "#333333"));
        context.setVariable("colorSecundario", valorOrDefault(catalogo.personalizacion().colorSecundario(), "#777777"));
        context.setVariable("logo", catalogo.personalizacion().logotipo());
        context.setVariable("productos", mapearProductos(catalogo.productos()));

        String nombreArchivoPlantilla = resolverNombreArchivo(catalogo.plantilla().nombrePlantilla());

        try {
            return pdfTemplateEngine.process(nombreArchivoPlantilla, context);
        } catch (TemplateInputException ex) {
            return pdfTemplateEngine.process(PLANTILLA_POR_DEFECTO, context);
        }
    }

    private String resolverNombreArchivo(String nombrePlantilla) {
        String sinTildes = Normalizer.normalize(nombrePlantilla, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return sinTildes.toLowerCase(Locale.ROOT)
                .trim()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }

    private List<Map<String, Object>> mapearProductos(List<ProductoDTO.Response> productos) {
        return productos.stream().map(p -> {
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("nombreProducto", p.nombreProducto());
            mapa.put("descripcion", p.descripcion());
            mapa.put("imgUrl", p.imgUrl());
            mapa.put("precioFormateado", formatearPrecio(p.precio()));
            return mapa;
        }).toList();
    }

    private byte[] convertirHtmlAPdf(String html) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF del catálogo: " + e.getMessage(), e);
        }
    }

    private String formatearPrecio(BigDecimal precio) {
        NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        return formato.format(precio);
    }

    private String valorOrDefault(String valor, String porDefecto) {
        return (valor == null || valor.isBlank()) ? porDefecto : valor;
    }
}