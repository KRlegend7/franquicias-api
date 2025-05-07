# Archivo: main.tf
provider "mongodbatlas" {
  public_key  = var.mongodb_atlas_api_pub_key
  private_key = var.mongodb_atlas_api_pri_key
}

resource "mongodbatlas_project" "franquicias_project" {
  name   = "franquicias-project"
  org_id = var.mongodb_atlas_org_id
}

resource "mongodbatlas_cluster" "franquicias_cluster" {
  project_id = mongodbatlas_project.franquicias_project.id
  name       = "franquicias-cluster"

  provider_name               = "AWS"
  provider_region_name        = "US_EAST_1"
  provider_instance_size_name = "M10"
  cloud_backup                = true
}

resource "mongodbatlas_database_user" "franquicias_user" {
  username           = "franquicias-app"
  password           = var.mongodb_atlas_user_password
  project_id         = mongodbatlas_project.franquicias_project.id
  auth_database_name = "admin"

  roles {
    role_name     = "readWrite"
    database_name = "franquicias"
  }
}

resource "mongodbatlas_project_ip_access_list" "ip_access_list" {
  project_id = mongodbatlas_project.franquicias_project.id
  cidr_block = "0.0.0.0/0"
  comment    = "Permitir acceso desde cualquier lugar (solo para desarrollo)"
}

output "connection_string" {
  value = "mongodb+srv://${mongodbatlas_database_user.franquicias_user.username}:${var.mongodb_atlas_user_password}@${mongodbatlas_cluster.franquicias_cluster.name}.mongodb.net/franquicias?retryWrites=true&w=majority"
}