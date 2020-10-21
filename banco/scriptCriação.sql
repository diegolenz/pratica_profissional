

	 create table Pais (
        id  serial not null,
        ativo boolean,
        ddi varchar(255),
        nome varchar(255),
		       	data_cadastro timestamp not null, 
		data_ultima_alteracao timestamp not null, 
		funcionario_cadastro_id int references funcionario not null,
		funcionario_ultima_alteracao int references funcionario not null,
        primary key (id)
    ); 
	
	

    create table Estado (
        id  serial not null,
        ativo boolean not null,
        nome varchar(99) not null,
        sigla varchar(255),
        pais_id int references pais(id) not null,
		       	data_cadastro timestamp not null, 
		data_ultima_alteracao timestamp not null, 
		funcionario_cadastro_id int references funcionario not null,
		funcionario_ultima_alteracao int references funcionario not null,
        primary key (id)
    ); 

    
	
    create table Cidade (
        id  serial not null,
        DDD varchar(10),
        ativo boolean,
        CODIGO_IBGE varchar(10),
        nome varchar(255) not null,
        estado_id int references estado(id) not null,
		       	data_cadastro timestamp not null, 
		data_ultima_alteracao timestamp not null, 
		funcionario_cadastro_id int references funcionario not null,
		funcionario_ultima_alteracao int references funcionario not null,
        primary key (id)
    );

	create table Fornecedor (
		id serial primary key, 
		cpf_Cnpj varchar(255),
        data_nascimento date,
        email varchar(255),
        estado_civil varchar(255),
        nome varchar(255),
        nome_fantasia_apelido varchar(255),
        rg_ie varchar(255),
        sexo int,
        telefone varchar(255),
        telefone_alternativo varchar(255),
        tipo varchar(255),
        cidade_id int references cidade(id),
		bairro varchar(255),
        cep varchar(255),
        complemento varchar(255),
        logradouro varchar(255) not null,
        numero_residencial varchar(255),
        referencia varchar(255),
		ativo boolean,
          	data_cadastro timestamp not null, 
		data_ultima_alteracao timestamp not null, 
		funcionario_cadastro_id int references funcionario not null,
		funcionario_ultima_alteracao int references funcionario not null
		
    ) ; 
    
    
    create table Cliente (
		id serial primary key, 
        ativo boolean,
		cpf_Cnpj varchar(255),
        data_nascimento date,
        email varchar(255),
        estado_civil varchar(255),
        nome varchar(255),
        nome_fantasia_apelido varchar(255),
        rg_ie varchar(255),
        sexo int,
        telefone varchar(255),
        telefone_alternativo varchar(255),
        tipo varchar(255),
        cidade_id int references cidade(id),
		bairro varchar(255),
        cep varchar(255),
        complemento varchar(255),
        logradouro varchar(255) not null,
        numero_residencial varchar(255),
        referencia varchar(255),
             	data_cadastro timestamp not null, 
		data_ultima_alteracao timestamp not null, 
		funcionario_cadastro_id int references funcionario not null,
		funcionario_ultima_alteracao int references funcionario not null
    );  
	
	  create table Funcionario (
		id serial primary key,
        ativo boolean not null,
		cpf_Cnpj varchar(255),
        data_nascimento date,
        email varchar(255),
        estado_civil varchar(255),
        nome varchar(255),
        nome_fantasia_apelido varchar(255),
        rg_ie varchar(255),
        sexo int,
        telefone varchar(255),
        telefone_alternativo varchar(255),
        tipo varchar(255),
        cidade_id int references cidade(id),
		bairro varchar(255),
        cep varchar(255),
        complemento varchar(255),
        logradouro varchar(255) not null,
        numero_residencial int,
        referencia varchar(255),
              	data_cadastro timestamp not null, 
		data_ultima_alteracao timestamp not null, 
		funcionario_cadastro_id int references funcionario not null,
		funcionario_ultima_alteracao int references funcionario not null,
        data_admissao timestamp,
        data_demissao timestamp,
        primeiro_login boolean not null,
        senha varchar(255),
        usuario varchar(255) 
    ) ; 
	
	 create table Forma_Pagamento (
       id  serial not null,
        ativo boolean,
        nome varchar(255),
        primary key (id),
		       	data_cadastro timestamp not null, 
		data_ultima_alteracao timestamp not null, 
		funcionario_cadastro_id int references funcionario not null,
		funcionario_ultima_alteracao int references funcionario not null
    ); 
	
	  create table Condicao_pagamento (
        id  serial not null,
        ativo boolean,
        desconto FLOAT,
        juros FLOAT,
        multa FLOAT,
        nome varchar(255),
		       	data_cadastro timestamp not null, 
		data_ultima_alteracao timestamp not null, 
		funcionario_cadastro_id int references funcionario not null,
		funcionario_ultima_alteracao int references funcionario not null,
        quantidadeParcelas int,
        primary key (id)
    ); 
	
	 create table Parcela (
        id  serial not null,
        ativo boolean,
        dias int,
        numero int,
        porcentagem FLOAT,
        forma_pagamento_id int not null references forma_pagamento ,
		Condicao_Pagamento_id int not null references condicao_pagamento ON DELETE CASCADE ON UPDATE CASCADE,
        primary key (id)
		
    ); 
    
   -- create table Condicao_Pagamento_Parcela (
     --   Condicao_Pagamento_id int not null references condicao_pagamento,
       -- parcelas_id int not null references parcela
    --);
	 
    create table Grupo (
       id  serial not null,
        ativo boolean,
        nome varchar(255),
		       	data_cadastro timestamp, 
		data_ultima_alteracao timestamp, 
		funcionario_cadastro_id int references funcionario,
		funcionario_ultima_alteracao int references funcionario,
        primary key (id)
    ); 
    
    create table grupo_funcionario (
		id  bigserial not null,
        nome varchar(255),
            	data_cadastro timestamp, 
		data_ultima_alteracao timestamp, 
		funcionario_cadastro_id int references funcionario,
		funcionario_ultima_alteracao int references funcionario,
		ativo boolean,
        primary key (id)
    ); 
    
    create table permissao_acesso (
        id  bigserial not null,
		Grupo_Funcionario_id INT not null references grupo_funcionario  ON DELETE CASCADE ON UPDATE CASCADE,
        modulo varchar(255) not null,
        nivel_Acesso varchar(255) not null,
        primary key (id)
    ); 
   
   create table relacao_grupo_funcionario (
        Funcionario_id INT references funcionario,
        grupo_id INT references grupo_funcionario,
		primary key(Funcionario_id, grupo_id)
    ); 
   
     create table Marca (
        id  serial not null,
        ativo boolean,
              	data_cadastro timestamp, 
		data_ultima_alteracao timestamp, 
		funcionario_cadastro_id int references funcionario,
		funcionario_ultima_alteracao int references funcionario,
		
        nome varchar(255),
        primary key (id)
    ); 

   
	create table compra (
		modelo varchar(250),
		numero int,
		serie int,
		fornecedor_id int references fornecedor(id), 
		data_chegada date,
		data_emissao date,
		funcionario_id int references funcionario(id),
		condicao_Pagamento_id int references condicao_pagamento(id),
		ativo boolean,
		tipo_frete int,
		outras_despesas float,
		valor_frete float,
		valor_seguro float,
		       	data_cadastro timestamp, 
		data_ultima_alteracao timestamp, 
		funcionario_cadastro_id int references funcionario,
		funcionario_ultima_alteracao int references funcionario,
		motivo_cancelamento varchar,
		CONSTRAINT pk_compra01 primary key (numero, serie, modelo, fornecedor_id)
	);
	
	create table venda_produto (
		modelo varchar(250),
		numero int,
		serie int,
		cliente_id int references cliente(id), 
		data_chegada date,
		data_emissao date,
		funcionario_id int references funcionario(id),
		condicao_Pagamentos_id int references condicao_pagamento(id),
		ativo boolean,
		tipo_frete int,
		outras_despesas float,
		valor_frete float,
		valor_seguro float,
		motivo_cancelamento varchar,
		       	data_cadastro timestamp, 
		data_ultima_alteracao timestamp, 
		funcionario_cadastro int references funcionario,
		funcionario_ultima_alteracao int references funcionario,
		CONSTRAINT pk_venda primary key (numero, serie, modelo)
	);
	
	create table venda_servico (
		modelo varchar(250),
		numero int,
		serie int,
		cliente_id int references cliente(id), 
		data_chegada date,
		data_emissao date,
		       	data_cadastro timestamp, 
		data_ultima_alteracao timestamp, 
		funcionario_cadastro_id int references funcionario,
		funcionario_ultima_alteracao int references funcionario,
		condicao_Pagamentos_id int references condicao_pagamento(id),
		ativo boolean,
		tipo_frete int,
		outras_despesas float,
		valor_frete float,
		valor_seguro float,
		motivo_cancelamento varchar,
		CONSTRAINT pk_venda01 primary key (numero, serie, modelo)
	);
	
	create table item_servico (
		id int primary  key,
		quantidade int, 
		desconto_unitario float,
		acrescimo_unitario float,
		valor_unitario float,
		servico_id float,
		numero_venda int not null ,
		serie_venda int not null,
		modelo_venda varchar not null,
		valor_rateio float,
		cliente_id int not null,
		valor_total float,
		FOREIGN KEY (numero_venda, serie_venda, modelo_venda) references venda_servico (numero, serie, modelo)
	);

	create table conta_pagar (
		id serial primary key, 
		modelo_compra varchar ,
		numero_compra int,
		descricao varchar,
		data_pagamento date,
		serie_compra int , 
		fornecedor_id int,
		data_lancamento date,
		data_vencimento date,
		forma_pagamento_id int references forma_pagamento(id),
		valor float ,
		paga boolean,
		valor_pago float,
		multa float,
		desconto float,
		juros float,
		FOREIGN KEY (numero_compra,serie_compra, modelo_compra, fornecedor_id) references compra (numero, serie, modelo, fornecedor_id),
		       	data_cadastro timestamp, 
		data_ultima_alteracao timestamp, 
		funcionario_cadastro_id int references funcionario,
		funcionario_ultima_alteracao int references funcionario,
	);
 
	CREATE TABLE public.conta_receber (
	id serial NOT NULL,
	modelo_venda varchar NULL,
	numero_venda int4 NULL,
	serie_venda int4 NULL,
	cliente_id int4 null references cliente,
	data_lancamento date NULL,
	data_vencimento date NULL,
	forma_pagamento_id int4 NULL,
	valor float8 NULL,
	paga bool NULL,
	valor_pago float8 NULL,
	multa float8 NULL,
	desconto float8 NULL,
	juros float8 NULL,
		descricao varchar,
		data_pagamento date,
	CONSTRAINT conta_receber_pkey PRIMARY KEY (id),
	       	data_cadastro timestamp, 
		data_ultima_alteracao timestamp, 
		funcionario_cadastro_id int references funcionario,
		funcionario_ultima_alteracao int references funcionario,
);

 
    create table Produto (
        id  serial not null,
        ativo boolean,
        codigo_barras varchar(255),
       	data_cadastro timestamp, 
		data_ultima_alteracao timestamp, 
		funcionario_cadastro_id int references funcionario,
		funcionario_ultima_alteracao int references funcionario,
		data_ultima_compra date,
        nome varchar(255),
        preco_compra FLOAT,
        quantidade_estoque int,
        quantidade_minima int,
        referencia varchar(255),
        unidade_medida varchar(255),
        valor FLOAT,
        grupo_id int references grupo,
        marca_id int references marca,
		ultimo_fornecedor_id int references fornecedor,
        primary key (id)
    ); 
    
	create table item_produto (
		id serial primary key,
		quantidade float not null,
		desconto_unitario float not null,
		acrescimo_unitario float not null, 
		valor_unitario float not null,
		valor_rateio float not null,
		valor_total float not null,
		produto_id int references produto (id),
		fornecedor_id int,
		numero_compra int ,
		modelo_compra varchar(250),
		serie_compra int,
		FOREIGN KEY (numero_compra,serie_compra, modelo_compra, fornecedor_id) references compra (numero, serie, modelo, fornecedor_id)
	);
	
	create table item_produto_venda (
		id serial primary key,
		quantidade float not null,
		desconto_unitario float not null,
		acrescimo_unitario float not null, 
		valor_unitario float not null,
		valor_rateio float not null,
		valor_total float not null,
		produto_id int references produto (id),
		fornecedor_id int,
		numero_venda int ,
		modelo_venda varchar(250),
		serie_venda int,
		FOREIGN KEY (numero_venda, serie_venda, modelo_venda) references venda_produto (numero, serie, modelo)
	);
	
    create table Servico (
       id  serial not null,
        valor FLOAT,
        ativo boolean,
        data_cadastro date,
        data_ultima_alteracao date,
        nome varchar(255),
        grupo_id int,
        primary key (id)
    ); 
	
	create table caixa(
		id serial primary key,
		nome varchar,
		data_cadastro date, 
		data_ultima_alteracao date,
		funcionario_cadastro_id int references funcionario,
		funcionario_altaracao_id int references funcionario,
		aberto boolean,
		ativo boolean
	);
		
	create table abertura_caixa (
		id serial primary key,
		caixa_id int references caixa, 
		data_abertura date, 
		valor float,
		funcionario_id int references funcionario
	);
	
	create table fechamento_caixa (
		id serial primary key,
		caixa_id int references caixa, 
		data_abertura date,
		valor_fechamento float,
		funcionario_id int references funcionario
	);
	
create table condicao_pagamento_fornecedor (
		condicao_id int references condicao_pagamento,
		fornecedor_id int references fornecedor,
		primary key (condicao_id , fornecedor_id)
);

create table condicao_pagamento_cliente (
		condicao_id int references condicao_pagamento,
		cliente_id int references cliente,
		primary key (condicao_id, cliente_id)
);

create table ordem_servico(
	id int primary key,
	data_cadastro date, 
	data_ultima_alteracao date, 
	funcionario_cadastro_id int references funcionario, 
	funcionario_ultima_atualizacao_id int references funcionario,
condicao_pagamento_id int references condicao_pagamento, cancelada boolean, numero_venda_produto int, serie_venda_produto int, modelo_venda_produto varchar, 
FOREIGN KEY (numero_venda_produto, serie_venda_produto, modelo_venda_produto) references venda_produto (numero, serie, modelo),
 numero_venda_servico int, serie_venda_servico int, modelo_venda_servico varchar, 
 FOREIGN KEY (numero_venda_servico, serie_venda_servico, modelo_venda_servico) references venda_produto (numero, serie, modelo),
 cliente_id int references cliente, encerada boolean
) ;

CREATE TABLE public.item_produto_os (
	produto_id int4 NOT null references produto,
	quantidade int4 NULL,
	desconto_unitario float8 NULL,
	acrescimo_unitario float8 NULL,
	valor_unitario float8 NULL,
	os_id int4 NOT null references ordem_servico,
	valor_rateio float8 NULL,
	cliente_id int4 NOT NULL,
	valor_total float8 NULL,
	CONSTRAINT item_p_pkey1 PRIMARY KEY (produto_id, os_id)
);

create table veiculo (
	id serial primary key, 
	nome varchar,
	ativo boolean, 
	data_cadastro date, 
	data_ultima_alteracao date, 
	funcionario_cadastro_id int references funcionario,
	funcionario_ultima_alteracao int references funcionario,
	chassis varchar(50), 
	placa varchar(15),
	cor varchar(50),
	modelo_id int references modelo
);

create table sistema_config (
	id serial primary key,
	razao_social_empresa varchar not null,
	nome_fantasia varchar,
	cnpj_empresa varchar not null,
	IE varchar , 
	logradouro varchar, 
	bairro varchar,
	numero varchar,
	complemento varchar,
	cidade_id int references cidade,
	condicao_pagamento_padrao_id int not null references condicao_pagamento
)

alter table ordem_servico add column responsavel_id int references funcionario;
alter table ordem_servico  alter column data_cadastro set not null;
	alter table cliente add column if not exists funcionario_cadastro int references funcionario(id);
alter table cliente add column if not exists funcionario_ultima_alteracao int references funcionario(id);
alter table fornecedor add column if not exists funcionario_cadastro int references funcionario(id);
alter table fornecedor add column if not exists funcionario_ultima_alteracao int references funcionario(id);
alter table cidade add column if not exists funcionario_cadastro int references funcionario(id);
alter table cidade add column if not exists funcionario_ultima_alteracao int references funcionario(id);
alter table cidade add column if not exists data_cadastro date not null, 
alter table cidade add column if not exists data_ultima_alteracao date not null;
alter table cidade add column if not exists data_cadastro date ;
alter table cidade add column if not exists data_ultima_alteracao date ;
alter table pais add column if not exists data_cadastro date ;
alter table pais add column if not exists data_ultima_alteracao date ;
alter table estado add column if not exists data_cadastro date ;
alter table estado add column if not exists data_ultima_alteracao date ;
alter table produto add column if not exists funcionario_cadastro int references funcionario(id);
alter table produto add column if not exists funcionario_ultima_alteracao int references funcionario(id);
alter table marca add column if not exists funcionario_cadastro int references funcionario(id);
alter table marca add column if not exists funcionario_ultima_alteracao int references funcionario(id);
alter table marca add column if not exists data_cadastro timestamp;
alter table marca add column if not exists data_ultima_alteracao timestamp ;


alter table conta_pagar add column if not exists funcionario_cadastro int references funcionario(id);
alter table conta_pagar add column if not exists funcionario_ultima_alteracao int references funcionario(id);
alter table conta_pagar add column if not exists data_cadastro timestamp;
alter table conta_pagar add column if not exists data_ultima_alteracao timestamp;

alter table conta_pagar add column if not exists funcionario_cadastro int references funcionario(id);
alter table conta_pagar add column if not exists funcionario_ultima_alteracao int references funcionario(id);
alter table conta_pagar add column if not exists data_cadastro timestamp;
alter table conta_pagar add column if not exists data_ultima_alteracao timestamp;
alter table conta_pagar add column if not exists ativo boolean;
alter table conta_receber add column if not exists ativo boolean;
alter table conta_receber add column if not exists funcionario_cadastro int references funcionario(id);
alter table conta_receber add column if not exists funcionario_ultima_alteracao int references funcionario(id);
alter table conta_receber add column if not exists data_cadastro timestamp;
alter table conta_receber add column if not exists data_ultima_alteracao timestamp;
alter table conta_pagar drop column if exists serie_compra;

alter table conta_pagar drop column if exists modelo_compra;
alter table conta_pagar drop column if exists num_compra;
alter table conta_receber drop column if exists serie_venda;
alter table conta_pagar drop column if exists modelo_venda;
alter table conta_pagar drop column if exists num_venda;
alter table permissao_acesso alter column nivel_Acesso type varchar;
alter table permissao_acesso alter column modulo type varchar;

alter table grupo_funcionario add column if not exists funcionario_cadastro int references funcionario(id) not null default 5;
alter table grupo_funcionario add column if not exists funcionario_ultima_alteracao int references funcionario(id) not null default 5;
alter table grupo_funcionario add column if not exists data_cadastro timestamp not null;
alter table grupo_funcionario add column if not exists data_ultima_alteracao timestamp not null;
alter table funcionario add column if not exists funcionario_cadastro int references funcionario(id) not null default 5;
alter table funcionario add column if not exists funcionario_ultima_alteracao int references funcionario(id) not null default 5;
alter table funcionario add column if not exists data_cadastro timestamp not null;
alter table funcionario add column if not exists data_ultima_alteracao timestamp not null;
alter table forma_pagamento add column if not exists funcionario_cadastro int references funcionario(id) not null default 5;
alter table forma_pagamento add column if not exists funcionario_ultima_alteracao int references funcionario(id) not null default 5;
alter table forma_pagamento add column if not exists data_cadastro timestamp not null default now();
alter table forma_pagamento add column if not exists data_ultima_alteracao timestamp not null default now();
alter table compra add column if not exists funcionario_cadastro int references funcionario(id) not null default 5;
alter table compra add column if not exists funcionario_ultima_alteracao int references funcionario(id) not null default 5;
alter table compra add column if not exists data_cadastro timestamp not null default now();
alter table compra add column if not exists data_ultima_alteracao timestamp not null default now();
alter table venda_servico add column if not exists funcionario_cadastro int references funcionario(id) not null default 5;
alter table venda_servico add column if not exists funcionario_ultima_alteracao int references funcionario(id) not null default 5;
alter table venda_servico add column if not exists data_cadastro timestamp not null default now();
alter table venda_servico add column if not exists data_ultima_alteracao timestamp not null default now();
alter table venda_produto add column if not exists funcionario_cadastro int references funcionario(id) not null default 5;
alter table venda_produto add column if not exists funcionario_ultima_alteracao int references funcionario(id) not null default 5;
alter table venda_produto add column if not exists data_cadastro timestamp not null default now();
alter table venda_produto add column if not exists data_ultima_alteracao timestamp not null default now();
alter table conta_receber add column if not exists is_venda_produto boolean  default false;
alter table conta_receber add column if not exists is_venda_servico  boolean  default false;

drop table if exists condicao_pagamento_parcela;
alter table parcela add column if not exists Condicao_Pagamento_id int not null references condicao_pagamento default 1;

-- delete regiao
  -- delete from item_produto_venda; delete from item_produto_os; delete from item_produto;
  -- delete from item_servico; delete from item_servico_os;
  -- delete from venda_produto; delete from ordem_servico; delete from venda_servico; delete from compra; 
  -- delete from condicao_pagamento_fornecedor;  delete from condicao_pagamento_cliente;
  -- delete from conta_pagar; delete from conta_receber; delete from condicao_pagamento;







