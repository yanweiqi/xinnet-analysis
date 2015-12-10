CREATE  TABLE IF NOT EXISTS COMPONENT(
  ip varchar(30) NOT NULL,
  port int(11) NOT NULL,
  comp_type varchar(20) DEFAULT NULL,
  description varchar(50) DEFAULT NULL,
  PRIMARY KEY (ip,port)
);

CREATE TABLE IF NOT EXISTS CompConstants (
  ip varchar(30) NOT NULL,
  port int(11) NOT NULL,
  queues varchar(40) DEFAULT NULL,
  manageListSize int(11) DEFAULT NULL,
  types varchar(30) DEFAULT NULL,
  saveThreadNum int(11) DEFAULT NULL,
  PRIMARY KEY (ip,port)
);

CREATE  TABLE IF NOT EXISTS Rule(
  id int(11) NOT NULL,
  ruleName  varchar(30) DEFAULT NULL,
  ruleValue  varchar(30) DEFAULT NULL,
  PRIMARY KEY (id)
);
 
CREATE  TABLE IF NOT EXISTS CompMonitor(
  id int(11) NOT NULL,
  ip varchar(30) NOT NULL,
  port int(11) NOT NULL,
  receiveCount int(11) DEFAULT NULL,
  collectorQueueCount int(11) DEFAULT NULL,
  collectorThreadStates int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);

