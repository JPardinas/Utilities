@echo off
echo ***************************************************************************
echo ** Montando Entorno de Proyecto                                          **
echo ** Todas las tareas deben ejecutarse desde S:                            **
echo ***************************************************************************
call subst S: .
set PATH=%PATH%