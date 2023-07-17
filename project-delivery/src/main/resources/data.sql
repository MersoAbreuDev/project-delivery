INSERT INTO tb_perfil (nome,data_criacao)
  SELECT 'admin',now()
    WHERE NOT EXISTS (SELECT nome FROM tb_perfil WHERE id = 1);

    INSERT INTO tb_usuario (data_criacao, login, password, id_perfil, status_usuario)
 SELECT  now(),'root@gmail.com','$2a$12$W4ga0wBDtSMXGY0y41/PAuY8o0E.SGucCpddVRY6gvZhPxA16bpGi', 1, 'ATIVO'
    WHERE NOT EXISTS (SELECT login FROM tb_usuario WHERE id  = 1);