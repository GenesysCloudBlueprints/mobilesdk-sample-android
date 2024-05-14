/*   
   Looks up the id of the user so we can associate it with the queue
*/
data "genesyscloud_user" "user" {
    email = var.email
}

/*   
   Creates the queue used within the flow
*/
module "queue" {
  source = "./modules/queue"
  userId = data.genesyscloud_user.user.id
}

/*   
   Configures the architect flow
*/
module "archy_flow" {
  source        = "./modules/flow"
  workflow_name = "Mobile SDK Message Flow"
  division      = var.division
  queue_name    = module.queue.queue_id
}

/*   
   Configures the web deployment configuration
*/
module "web_config" {
  source = "./modules/webdeployments_configuration"
}

/*   
   Configures the web deployment
*/
module "web_deploy" {
  source      = "./modules/webdeployments_deployment"
  flowId      = module.archy_flow.inbound_message_flow_id
  configId    = module.web_config.config_id
  configVer   = module.web_config.config_ver
}