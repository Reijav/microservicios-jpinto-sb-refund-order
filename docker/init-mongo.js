db = db.getSiblingDB("order_read");

db.createUser({
  user: "order_read_user",
  pwd: "order_read_pass",
  roles: [
    { role: "readWrite", db: "order_read" }
  ]
});