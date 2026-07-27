package CatagoloEmprende.service;

import CatagoloEmprende.dto.TiendaDTO;
import CatagoloEmprende.exception.CorreoYaRegistradoException;
import CatagoloEmprende.exception.RecursoNoEncontradoException;
import CatagoloEmprende.mapper.TiendaMapper;
import CatagoloEmprende.model.Tienda;
import CatagoloEmprende.repository.TiendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TiendaServiceImpl implements TiendaService {

    private final TiendaRepository tiendaRepository;
    private final TiendaMapper tiendaMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public TiendaDTO.Response registrar(TiendaDTO.RegistroRequest request) {
        if (tiendaRepository.existsByCorreo(request.correo())) {
            throw new CorreoYaRegistradoException(
                    "Ya existe una tienda registrada con el correo: " + request.correo());
        }

        Tienda tienda = tiendaMapper.toEntity(request);
        // Nunca se guarda la contraseña tal cual llega del formulario.
        tienda.setPassword(passwordEncoder.encode(request.password()));

        Tienda guardada = tiendaRepository.save(tienda);
        return tiendaMapper.toResponse(guardada);
    }

    @Override
    public TiendaDTO.Response buscarPorId(Long id) {
        return tiendaMapper.toResponse(obtenerEntidadPorId(id));
    }

    @Override
    public Tienda obtenerEntidadPorId(Long id) {
        return tiendaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró la tienda con id: " + id));
    }
}
