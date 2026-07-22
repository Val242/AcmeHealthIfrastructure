# Task 3: RabbitMQ Setup

## Objective

Install and configure RabbitMQ on the message queue server (`rmq01`).

Requirements:

- Install RabbitMQ
- Create `devops` RabbitMQ user
- Grant permissions
- Open firewall port 5672
- Verify connectivity from application servers

---

# Server Information

| Server | IP Address | Role |
|---|---|---|
| rmq01 | 192.168.56.23 | RabbitMQ Server |
| app01 | 192.168.56.21 | Application Server |
| app02 | 192.168.56.22 | Application Server |

---

# Installation

## Add RabbitMQ Repository

Command:

```bash
sudo dnf -y install centos-release-rabbitmq-38
```

Purpose:

Adds the RabbitMQ repository so that RabbitMQ packages can be installed using `dnf`.

---

## Install RabbitMQ Server

Command:

```bash
sudo dnf --enablerepo=centos-rabbitmq-38 -y install rabbitmq-server
```

Purpose:

Installs RabbitMQ server and required dependencies.

---

# RabbitMQ Service Configuration

Start RabbitMQ:

```bash
sudo systemctl start rabbitmq-server
```

Enable RabbitMQ at boot:

```bash
sudo systemctl enable rabbitmq-server
```

Verify service status:

```bash
sudo systemctl status rabbitmq-server
```

Purpose:

Ensures RabbitMQ is running and automatically starts after system reboot.

---

# RabbitMQ User Configuration

Create RabbitMQ user:

```bash
sudo rabbitmqctl add_user devops <password>
```

Purpose:

Creates a dedicated RabbitMQ user instead of using the default guest account.

---

Assign administrator privileges:

```bash
sudo rabbitmqctl set_user_tags devops administrator
```

Purpose:

Allows the `devops` user to manage RabbitMQ.

---

Grant permissions:

```bash
sudo rabbitmqctl set_permissions -p / devops ".*" ".*" ".*"
```

Purpose:

Grants the user permissions to configure, read, and write on the default RabbitMQ virtual host.

---

# Firewall Configuration

Start firewall:

```bash
sudo systemctl start firewalld
```

Enable firewall:

```bash
sudo systemctl enable firewalld
```

Open RabbitMQ AMQP port:

```bash
sudo firewall-cmd --add-port=5672/tcp --permanent
```

Reload firewall:

```bash
sudo firewall-cmd --reload
```

Purpose:

Allows application servers to communicate with RabbitMQ using the AMQP protocol.

---

# Connectivity Verification

Installed telnet client on application servers:

```bash
sudo dnf install telnet -y
```

Tested connectivity from `app01`:

```bash
telnet rmq01 5672
```

Result:

```
Connected to rmq01.
Escape character is '^]'.
Connection closed by foreign host.
```

Tested connectivity from `app02`:

```bash
telnet rmq01 5672
```

Result:

```
Connected to rmq01.
Escape character is '^]'.
Connection closed by foreign host.
```

---

# Conclusion

RabbitMQ has been successfully deployed on `rmq01`.

Completed:

- RabbitMQ installation 
- RabbitMQ service configuration 
- User creation and permissions 
- Firewall configuration 
- Connectivity verification from app servers 

Task 3 completed successfully.