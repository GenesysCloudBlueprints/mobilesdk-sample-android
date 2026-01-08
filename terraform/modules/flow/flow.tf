resource "genesyscloud_flow" "Inbound_Message_Flow" {
  filepath          = "${path.module}/mobile-sdk-inbound-message-flow.yaml"
  substitutions = {
    workflow_name = var.workflow_name
    division      = var.division
    queue_name    = var.queue_name
  }
}