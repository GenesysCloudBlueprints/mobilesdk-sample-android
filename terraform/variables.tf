variable "email" {
  type        = string
  description = "This value is the email account that you use with Genesys Cloud. It will be used to assign you to the appropriate Genesys Cloud queue."
}

variable "division" {
    type        = string
    description = "The division to which the flow is deployed. By default, the new flows are deployed to the **Home** division."
}