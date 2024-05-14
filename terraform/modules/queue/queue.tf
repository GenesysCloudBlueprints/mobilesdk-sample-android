/*
  Creates the queue
*/
resource "genesyscloud_routing_queue" "queue" {
  name = "Mobile SDK Queue"
  members {
    user_id  = var.userId
  }
}
