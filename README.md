# ms-objectstore
Micro serviço responsável para enviar arquivos para Bucket S3 e demais


Role API:
 CRUD

Role USER (para logados):
 GET Trilha/{id}: listar cursos paginados
 GET Curso/{id}: listar modulos
 GET Modulos/{id}: listar lições

Permit all:
 GET Trilha: listar trilhas paginadas com 3 cursos que sejam featured;

Se não estiver logado
  - listar somente as trilhas com 3 cursos para cada (GET Trilha)

Se usuário não for pago
  - trazer somente as 3 primeiras aulas do primeiro módulo

  - ![image](https://github.com/user-attachments/assets/bd7d0720-7167-4c0f-9cac-9904f6b09f37)
