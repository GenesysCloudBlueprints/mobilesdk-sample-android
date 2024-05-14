/*
  Creates the messaging deployment and assigns the flow
*/
resource "genesyscloud_webdeployments_deployment" "Mobile_SDK_Web_Deployment" {
  name                    = "Mobile SDK Web Deployment"
  flow_id                 = var.flowId
  status                  = "Active"
  allow_all_domains       = true
  allowed_domains         = []
  configuration {
    id      = var.configId
    version = var.configVer
  }
}
