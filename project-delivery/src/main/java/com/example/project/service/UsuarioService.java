package com.example.project.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.project.config.jwt.JWTConstants;
import com.example.project.entity.Perfil;
import com.example.project.entity.Usuario;
import com.example.project.enums.StatusUsuario;
import com.example.project.handle.request.BadRequestException;
import com.example.project.handle.response.ObjectNotFoundException;
import com.example.project.repository.UsuarioRepository;
import com.example.project.requestDTO.LoginRequestDTO;
import com.example.project.requestDTO.UsuarioPasswordRequestDTO;
import com.example.project.requestDTO.UsuarioRequestDTO;
import com.example.project.responseDTO.MensagemResponseDTO;
import com.example.project.responseDTO.PerfilResponseDTO;
import com.example.project.responseDTO.UsuarioResponseDTO;
import com.example.project.responseDTO.UsuarioTokenResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;

    private final PasswordEncoder passwordEncod;

    private final PerfilService perfilService;

    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncod,
                          PerfilService perfilService, ModelMapper modelMapper) {
        this.repository = repository;
        this.passwordEncod = passwordEncod;
        this.perfilService = perfilService;
        this.modelMapper = modelMapper;
    }

    public Usuario buscarUsuarioPeloLogin(String username) {
        return this.repository.findByLogin(username)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByLogin(username).map(usuario -> {
            return User.builder().username(usuario.getLogin()).password(usuario.getPassword()).roles(new String[] {})
                    .build();
        }).orElseThrow(() -> new BadRequestException("Login invalido."));
    }

    public UsuarioResponseDTO save(UsuarioRequestDTO usuarioRequestDTO) {
        this.repository.findByLogin(usuarioRequestDTO.getLogin()).ifPresent(usuario -> {
            throw new BadRequestException("Usuário já cadastrado.");
        });
        Perfil perfil = this.perfilService.buscarPerfilPeloId(usuarioRequestDTO.getIdPerfil());
        String passowrdEncrypted = passwordEncod.encode(usuarioRequestDTO.getPassword());
        usuarioRequestDTO.setPassword(passowrdEncrypted);
        Usuario usuario = this.modelMapper.map(usuarioRequestDTO, Usuario.class);
        usuario.setStatusUsuario(StatusUsuario.ATIVO);
        usuario.setPerfil(perfil);
        usuario = this.repository.save(usuario);
        return this.modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    public List<UsuarioResponseDTO> buscarTodos() {
        return this.repository.findAll().stream().map(usuario -> {
            return this.modelMapper.map(usuario, UsuarioResponseDTO.class);
        }).collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPeloId(Long id) {
        return this.repository.findById(id).map(usuario -> {
            return modelMapper.map(usuario, UsuarioResponseDTO.class);
        }).orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
    }

    public Usuario buscarUsuarioPeloId(Long idUsuario) {
        return this.repository.findById(idUsuario)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
    }

    public UsuarioTokenResponseDTO gerarTokenPeloUsuarioESenha(UsuarioPasswordRequestDTO usuarioPasswordRequestDTO) {

        return this.repository.findByLogin(usuarioPasswordRequestDTO.getLogin()).map(usuario -> {

            boolean validarSenha = passwordEncod.matches(usuarioPasswordRequestDTO.getPassword(),
                    usuario.getPassword());

            if (!validarSenha) {
                throw new BadRequestException("Senha invalida");
            }

            String token = JWT.create().withSubject(usuario.getLogin())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JWTConstants.TOKEN_EXPIRADO))
                    .sign(Algorithm.HMAC512(JWTConstants.CHAVE_ASSINATURA));

            UsuarioResponseDTO usuarioResposta = new UsuarioResponseDTO();
            usuarioResposta.setId(usuario.getId());
            usuarioResposta.setLogin(usuario.getLogin());
            usuarioResposta.setStatusUsuario(usuario.getStatusUsuario());

            PerfilResponseDTO perfilResponseDTO = new PerfilResponseDTO();
            perfilResponseDTO.setId(usuario.getPerfil().getId());
            perfilResponseDTO.setNome(usuario.getPerfil().getNome());
            usuarioResposta.setPerfil(perfilResponseDTO);

            UsuarioTokenResponseDTO tokenResponseDTO = new UsuarioTokenResponseDTO();
            tokenResponseDTO.setUsuario(usuarioResposta);
            tokenResponseDTO.setToken(token);

            return tokenResponseDTO;

        }).orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));

    }

}