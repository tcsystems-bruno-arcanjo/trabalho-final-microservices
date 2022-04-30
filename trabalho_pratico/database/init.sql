\c POSTGRES_INITDB;

CREATE TABLE tb_conta_corrente (
    id BIGSERIAL NOT NULL,
    nome VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE tb_movimentacao (
    id BIGSERIAL NOT NULL,
    id_conta_corrente BIGINT NOT NULL,
    horario TIMESTAMP NOT NULL,
    tipo INT NOT NULL,
    valor DECIMAL NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT tb_movimentacao_conta_corrente_fkey FOREIGN KEY(id_conta_corrente) REFERENCES tb_conta_corrente(id)
);

CREATE TABLE tb_despesa_cartao (
    id BIGSERIAL NOT NULL,
    id_conta_corrente BIGINT NOT NULL,
    horario TIMESTAMP NOT NULL,
    valor DECIMAL NOT NULL,
	paga BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
    CONSTRAINT tb_despesa_cartao_conta_corrente_fkey FOREIGN KEY(id_conta_corrente) REFERENCES tb_conta_corrente(id)
);

CREATE TABLE tb_investimento (
    id BIGSERIAL NOT NULL,
    id_conta_corrente BIGINT NOT NULL,
    horario TIMESTAMP NOT NULL,
    valor DECIMAL NOT NULL,
	resgatado BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
    CONSTRAINT tb_investimento_conta_corrente_fkey FOREIGN KEY(id_conta_corrente) REFERENCES tb_conta_corrente(id)
);

CREATE TABLE tb_usuario (
	id BIGSERIAL NOT NULL,
    usuario VARCHAR(255) NOT NULL,
	senha VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO tb_conta_corrente(nome)VALUES('Conta padrao');
INSERT INTO tb_usuario(usuario, senha)VALUES('admin', '$2y$10$VZphTIDS7EYTQKmM6tDuH.PxmOjuXyVjLzt8DanLtJ6Z0rVFdroMu');
