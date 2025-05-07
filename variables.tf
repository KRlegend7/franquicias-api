# Archivo: variables.tf
variable "mongodb_atlas_api_pub_key" {
  description = "Clave pública API de MongoDB Atlas"
  type        = string
}

variable "mongodb_atlas_api_pri_key" {
  description = "Clave privada API de MongoDB Atlas"
  type        = string
}

variable "mongodb_atlas_org_id" {
  description = "ID de organización en MongoDB Atlas"
  type        = string
}

variable "mongodb_atlas_user_password" {
  description = "Contraseña para usuario de base de datos"
  type        = string
}