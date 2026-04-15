-- Create database
drop database if exists db_sistema_de_ventas;
create database db_sistema_de_ventas;
use db_sistema_de_ventas;

-- Create tables
create table Clientes(
    dpi_cliente int not null,
    nombre_cliente varchar(50) not null,
    apellido_cliente varchar(50) not null,
    direccion varchar(100) not null,
    estado int not null,
    primary key PK_dpi_cliente(dpi_cliente)
);

create table Usuarios(
    codigo_usuario int auto_increment not null,
    username varchar(45) not null,
    password varchar(45) not null,
    email varchar(60) not null,
    rol varchar(45) not null,
    estado int not null,
    primary key PK_codigo_usuario(codigo_usuario)
);

create table Productos(
    codigo_producto int auto_increment not null,
    nombre_producto varchar(60) not null,
    precio decimal(10,2) not null,
    stock int not null,
    estado int not null,
    primary key PK_codigo_producto(codigo_producto)
);

create table Ventas(
    codigo_venta int auto_increment not null,
    fecha_venta date not null,
    total decimal(10,2) not null,
    estado int not null,
    Clientes_dpi_cliente int not null,
    Usuarios_codigo_usuario int not null,
    primary key PK_codigo_venta(codigo_venta),
    constraint FK_ventas_clientes foreign key (Clientes_dpi_cliente)
	references Clientes(dpi_cliente) on delete cascade,
    constraint FK_ventas_usuarios foreign key (Usuarios_codigo_usuario)
	references Usuarios(codigo_usuario) on delete cascade
);

create table DetalleVenta(
    codigo_detalle_venta int auto_increment not null,
    cantidad int not null,
    precio_unitario decimal(10,2) not null,
    subtotal decimal(10,2) not null,
    Productos_codigo_producto int not null,
    Ventas_codigo_venta int not null,
    primary key PK_codigo_detalle_venta(codigo_detalle_venta),
    constraint FK_detalleventa_productos foreign key (Productos_codigo_producto)
	references Productos(codigo_producto) on delete cascade,
    constraint FK_detalleventa_ventas foreign key (Ventas_codigo_venta)
	references Ventas(codigo_venta) on delete cascade
);

-- Stored procedures for Clientes
delimiter $$
create procedure sp_crearcliente(
    in p_dpi_cliente int,
    in p_nombre_cliente varchar(50),
    in p_apellido_cliente varchar(50),
    in p_direccion varchar(100),
    in p_estado int
)
begin
    insert into Clientes(dpi_cliente, nombre_cliente, apellido_cliente, direccion, estado)
    values(p_dpi_cliente, p_nombre_cliente, p_apellido_cliente, p_direccion, p_estado);
end $$
delimiter ;

delimiter $$
create procedure sp_listarclientes()
begin
    select * from Clientes;
end $$
delimiter ;

delimiter $$
create procedure sp_buscarcliente(in p_dpi_cliente int)
begin
    select * from Clientes where dpi_cliente = p_dpi_cliente;
end $$
delimiter ;

delimiter $$
create procedure sp_actualizarcliente(
    in p_dpi_cliente int,
    in p_nombre_cliente varchar(50),
    in p_apellido_cliente varchar(50),
    in p_direccion varchar(100),
    in p_estado int
)
begin
    update Clientes 
    set nombre_cliente = p_nombre_cliente,
        apellido_cliente = p_apellido_cliente,
        direccion = p_direccion,
        estado = p_estado
    where dpi_cliente = p_dpi_cliente;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarcliente(in p_dpi_cliente int)
begin
    delete from Clientes where dpi_cliente = p_dpi_cliente;
end $$
delimiter ;

-- Stored procedures for Usuarios
delimiter $$
create procedure sp_crearusuario(
    in p_username varchar(45),
    in p_password varchar(45),
    in p_email varchar(60),
    in p_rol varchar(45),
    in p_estado int
)
begin
    insert into Usuarios(username, password, email, rol, estado)
    values(p_username, p_password, p_email, p_rol, p_estado);
end $$
delimiter ;

delimiter $$
create procedure sp_listarusuarios()
begin
    select * from Usuarios;
end $$
delimiter ;

delimiter $$
create procedure sp_buscarusuario(in p_codigo_usuario int)
begin
    select * from Usuarios where codigo_usuario = p_codigo_usuario;
end $$
delimiter ;

delimiter $$
create procedure sp_actualizarusuario(
    in p_codigo_usuario int,
    in p_username varchar(45),
    in p_password varchar(45),
    in p_email varchar(60),
    in p_rol varchar(45),
    in p_estado int
)
begin
    update Usuarios 
    set username = p_username,
        password = p_password,
        email = p_email,
        rol = p_rol,
        estado = p_estado
    where codigo_usuario = p_codigo_usuario;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarusuario(in p_codigo_usuario int)
begin
    delete from Usuarios where codigo_usuario = p_codigo_usuario;
end $$
delimiter ;

-- Stored procedures for Productos
delimiter $$
create procedure sp_crearproducto(
    in p_nombre_producto varchar(60),
    in p_precio decimal(10,2),
    in p_stock int,
    in p_estado int
)
begin
    insert into Productos(nombre_producto, precio, stock, estado)
    values(p_nombre_producto, p_precio, p_stock, p_estado);
end $$
delimiter ;

delimiter $$
create procedure sp_listarproductos()
begin
    select * from Productos;
end $$
delimiter ;

delimiter $$
create procedure sp_buscarproducto(in p_codigo_producto int)
begin
    select * from Productos where codigo_producto = p_codigo_producto;
end $$
delimiter ;

delimiter $$
create procedure sp_actualizarproducto(
    in p_codigo_producto int,
    in p_nombre_producto varchar(60),
    in p_precio decimal(10,2),
    in p_stock int,
    in p_estado int
)
begin
    update Productos 
    set nombre_producto = p_nombre_producto,
        precio = p_precio,
        stock = p_stock,
        estado = p_estado
    where codigo_producto = p_codigo_producto;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarproducto(in p_codigo_producto int)
begin
    delete from Productos where codigo_producto = p_codigo_producto;
end $$
delimiter ;

-- Stored procedures for Ventas
delimiter $$
create procedure sp_crearventa(
    in p_fecha_venta date,
    in p_total decimal(10,2),
    in p_estado int,
    in p_Clientes_dpi_cliente int,
    in p_Usuarios_codigo_usuario int
)
begin
    insert into Ventas(fecha_venta, total, estado, Clientes_dpi_cliente, Usuarios_codigo_usuario)
    values(p_fecha_venta, p_total, p_estado, p_Clientes_dpi_cliente, p_Usuarios_codigo_usuario);
end $$
delimiter ;

delimiter $$
create procedure sp_listarventas()
begin
    select v.*, 
           c.nombre_cliente,
           c.apellido_cliente,
           u.username
    from Ventas v
    inner join Clientes c on v.Clientes_dpi_cliente = c.dpi_cliente
    inner join Usuarios u on v.Usuarios_codigo_usuario = u.codigo_usuario;
end $$
delimiter ;

delimiter $$
create procedure sp_buscarventa(in p_codigo_venta int)
begin
    select v.*, 
           c.nombre_cliente,
           c.apellido_cliente,
           u.username
    from Ventas v
    inner join Clientes c on v.Clientes_dpi_cliente = c.dpi_cliente
    inner join Usuarios u on v.Usuarios_codigo_usuario = u.codigo_usuario
    where v.codigo_venta = p_codigo_venta;
end $$
delimiter ;

delimiter $$
create procedure sp_actualizarventa(
    in p_codigo_venta int,
    in p_fecha_venta date,
    in p_total decimal(10,2),
    in p_estado int,
    in p_Clientes_dpi_cliente int,
    in p_Usuarios_codigo_usuario int
)
begin
    update Ventas 
    set fecha_venta = p_fecha_venta,
        total = p_total,
        estado = p_estado,
        Clientes_dpi_cliente = p_Clientes_dpi_cliente,
        Usuarios_codigo_usuario = p_Usuarios_codigo_usuario
    where codigo_venta = p_codigo_venta;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarventa(in p_codigo_venta int)
begin
    delete from Ventas where codigo_venta = p_codigo_venta;
end $$
delimiter ;

-- Stored procedures for DetalleVenta
delimiter $$
create procedure sp_creardetalleventa(
    in p_cantidad int,
    in p_precio_unitario decimal(10,2),
    in p_subtotal decimal(10,2),
    in p_Productos_codigo_producto int,
    in p_Ventas_codigo_venta int
)
begin
    insert into DetalleVenta(cantidad, precio_unitario, subtotal, Productos_codigo_producto, Ventas_codigo_venta)
    values(p_cantidad, p_precio_unitario, p_subtotal, p_Productos_codigo_producto, p_Ventas_codigo_venta);
end $$
delimiter ;

delimiter $$
create procedure sp_listardetalleventa()
begin
    select dv.*, 
           p.nombre_producto,
           v.fecha_venta,
           c.nombre_cliente,
           c.apellido_cliente
    from DetalleVenta dv
    inner join Productos p on dv.Productos_codigo_producto = p.codigo_producto
    inner join Ventas v on dv.Ventas_codigo_venta = v.codigo_venta
    inner join Clientes c on v.Clientes_dpi_cliente = c.dpi_cliente;
end $$
delimiter ;

delimiter $$
create procedure sp_buscardetalleventa(in p_codigo_detalle_venta int)
begin
    select dv.*, 
           p.nombre_producto,
           v.fecha_venta,
           c.nombre_cliente,
           c.apellido_cliente
    from DetalleVenta dv
    inner join Productos p on dv.Productos_codigo_producto = p.codigo_producto
    inner join Ventas v on dv.Ventas_codigo_venta = v.codigo_venta
    inner join Clientes c on v.Clientes_dpi_cliente = c.dpi_cliente
    where dv.codigo_detalle_venta = p_codigo_detalle_venta;
end $$
delimiter ;

delimiter $$
create procedure sp_actualizardetalleventa(
    in p_codigo_detalle_venta int,
    in p_cantidad int,
    in p_precio_unitario decimal(10,2),
    in p_subtotal decimal(10,2),
    in p_Productos_codigo_producto int,
    in p_Ventas_codigo_venta int
)
begin
    update DetalleVenta 
    set cantidad = p_cantidad,
        precio_unitario = p_precio_unitario,
        subtotal = p_subtotal,
        Productos_codigo_producto = p_Productos_codigo_producto,
        Ventas_codigo_venta = p_Ventas_codigo_venta
    where codigo_detalle_venta = p_codigo_detalle_venta;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminardetalleventa(in p_codigo_detalle_venta int)
begin
    delete from DetalleVenta where codigo_detalle_venta = p_codigo_detalle_venta;
end $$
delimiter ;

-- Additional useful procedures
delimiter $$
create procedure sp_ventasporfecha(in p_fecha date)
begin
    select v.*, 
           c.nombre_cliente,
           c.apellido_cliente,
           u.username
    from Ventas v
    inner join Clientes c on v.Clientes_dpi_cliente = c.dpi_cliente
    inner join Usuarios u on v.Usuarios_codigo_usuario = u.codigo_usuario
    where v.fecha_venta = p_fecha;
end $$
delimiter ;

delimiter $$
create procedure sp_detalleventaporventa(in p_codigo_venta int)
begin
    select dv.*, 
           p.nombre_producto,
           p.precio as precio_actual
    from DetalleVenta dv
    inner join Productos p on dv.Productos_codigo_producto = p.codigo_producto
    where dv.Ventas_codigo_venta = p_codigo_venta;
end $$
delimiter ;

delimiter $$
create procedure sp_productosbajostock(in p_stock_minimo int)
begin
    select * from Productos 
    where stock <= p_stock_minimo 
    and estado = 1;
end $$
delimiter ;

-- Insert sample data
-- Insert Clientes
call sp_crearcliente(123456789, 'juan', 'pérez', 'calle principal 123', 1);
call sp_crearcliente(987654321, 'maría', 'gonzález', 'avenida central 456', 1);
call sp_crearcliente(456789123, 'carlos', 'rodríguez', 'zona comercial 789', 1);
call sp_crearcliente(789123456, 'ana', 'martínez', 'boulevard norte 321', 1);
call sp_crearcliente(321654987, 'luis', 'hernández', 'calle secundaria 654', 1);

-- Insert Usuarios
call sp_crearusuario('admin', 'admin123', 'admin@sistema.com', 'administrador', 1);
call sp_crearusuario('vendedor1', 'pass123', 'vendedor1@sistema.com', 'vendedor', 1);
call sp_crearusuario('vendedor2', 'pass123', 'vendedor2@sistema.com', 'vendedor', 1);
call sp_crearusuario('gerente', 'gerente123', 'gerente@sistema.com', 'gerente', 1);
call sp_crearusuario('cajero', 'cajero123', 'cajero@sistema.com', 'cajero', 1);

-- Insert Productos
call sp_crearproducto('filtro de aceite', 15.00, 50, 1);
call sp_crearproducto('pastillas de freno', 45.00, 30, 1);
call sp_crearproducto('batería 12v 60ah', 110.00, 15, 1);
call sp_crearproducto('amortiguador delantero', 75.00, 20, 1);
call sp_crearproducto('bujía de encendido', 12.00, 100, 1);
call sp_crearproducto('aceite motor 5w30', 30.00, 40, 1);
call sp_crearproducto('correa de distribución', 60.00, 25, 1);
call sp_crearproducto('faro delantero derecho', 80.00, 10, 1);
call sp_crearproducto('radiador', 150.00, 8, 1);
call sp_crearproducto('sensor de oxígeno', 95.00, 12, 1);

-- Insert Ventas
call sp_crearventa('2024-01-15', 30.00, 1, 123456789, 2);
call sp_crearventa('2024-01-16', 45.00, 1, 987654321, 3);
call sp_crearventa('2024-01-17', 110.00, 1, 456789123, 2);
call sp_crearventa('2024-01-18', 150.00, 1, 789123456, 3);
call sp_crearventa('2024-01-19', 48.00, 1, 321654987, 2);

-- Insert DetalleVenta
call sp_creardetalleventa(2, 15.00, 30.00, 1, 1);
call sp_creardetalleventa(1, 45.00, 45.00, 2, 2);
call sp_creardetalleventa(1, 110.00, 110.00, 3, 3);
call sp_creardetalleventa(2, 75.00, 150.00, 4, 4);
call sp_creardetalleventa(4, 12.00, 48.00, 5, 5);