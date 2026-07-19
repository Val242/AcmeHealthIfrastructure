# DevOps Lab Journey Documentation

## Project: Acme Health Infrastructure Simulation

## Objective

Build a realistic multi-server infrastructure environment using Vagrant and VirtualBox, simulating how a DevOps engineer would provision, configure, and manage application infrastructure.

The target architecture:

```
                 Internet
                    |
                 Nginx
              (Reverse Proxy)
                    |
        -------------------------
        |                       |
   Spring Boot App #1     Spring Boot App #2
        |                       |
        -------- RabbitMQ -------
                    |
                 MariaDB
```

Infrastructure:

| Machine | Role                | OS              | IP            |
| ------- | ------------------- | --------------- | ------------- |
| web01   | Nginx Reverse Proxy | Ubuntu          | 192.168.56.20 |
| app01   | Application Server  | CentOS Stream 9 | 192.168.56.21 |
| app02   | Application Server  | CentOS Stream 9 | 192.168.56.22 |
| rmq01   | RabbitMQ Server     | CentOS Stream 9 | 192.168.56.23 |
| db01    | Database Server     | CentOS Stream 9 | 192.168.56.24 |

---

# Phase 1: Infrastructure Planning

Before writing configuration, the architecture was designed.

The focus was not only creating virtual machines, but understanding the responsibilities of each server:

* Web layer
* Application layer
* Messaging layer
* Database layer

The project was approached as an infrastructure project rather than just a coding exercise.

---

# Phase 2: Creating Virtual Machines with Vagrant

A Vagrantfile was created to define the environment.

The initial machines were:

```
web01
app01
app02
rmq01
db01
```

Each machine received:

* A hostname
* A private IP address
* A Linux distribution
* VirtualBox resources

Example:

```ruby
web01.vm.hostname = "web01"
web01.vm.network "private_network", ip: "192.168.56.20"
```

---

# Phase 3: First Infrastructure Issues Encountered

During the first deployment, several problems appeared.

## SSH Issue

The first attempt to access `web01` failed.

Investigation showed:

* VM was running
* SSH port was available
* The problem was related to the VM boot process

Debugging was performed using:

```bash
vagrant ssh-config web01
```

and:

```bash
vagrant ssh web01 -- -vvv
```

---

## Kernel Panic / VM Stability Issue

The initial Ubuntu image caused boot problems.

The issue was investigated and the decision was made to replace it with:

```
ubuntu/jammy64
```

The VM was removed and recreated.

This demonstrated an important infrastructure lesson:

> The operating system image is part of your infrastructure reliability.

---

# Phase 4: Resource Configuration

The VMs were optimized by defining memory allocations.

Example:

```ruby
vb.memory = "800"
```

The infrastructure was adjusted to better match the available hardware.

The importance of resource planning was learned:

* Too little memory affects stability.
* Too many resources waste capacity.

---

# Phase 5: Hostname Resolution and Networking

Private networking was configured.

Each VM received a static private IP:

```
192.168.56.20
192.168.56.21
192.168.56.22
192.168.56.23
192.168.56.24
```

Connectivity was tested.

Example:

```bash
ping app01
```

Result:

```
64 bytes from app01 (192.168.56.21)
```

Successful communication between machines confirmed the private network was working.

---

# Phase 6: Understanding Host Resolution

The `/etc/hosts` file was explored.

Initially, machines only knew their own hostname.

Example:

```
127.0.1.1 rmq01 rmq01
```

The concept of hostname resolution was learned:

```
hostname
        |
        |
 /etc/hosts
        |
        |
      IP address
```

The Vagrant HostManager plugin was discovered:

```ruby
config.hostmanager.enabled = true
config.hostmanager.manage_host = true
```

This automates hostname mapping.

---

# Phase 7: Installing Services

The database server initially included MariaDB provisioning.

The project explored:

```bash
yum install mariadb-server
```

and:

```bash
systemctl start mariadb
systemctl enable mariadb
```

A key lesson was learned:

> Reloading a VM does not destroy installed software.

`vagrant reload` restarts and reconfigures the machine; it does not rebuild it.

---

# Phase 8: Moving Toward Production Practices

A major design decision was made:

Instead of using the default Vagrant user for operations, create a dedicated:

```
devops
```

user.

This reflects real-world infrastructure practices.

Final user model:

```
root
 |
 |-- initial configuration
 |
devops
 |
 |-- daily administration
 |-- SSH access
 |-- sudo privileges
```

---

# Phase 9: Creating DevOps User

The `devops` user was created on:

```
web01
app01
app02
rmq01
db01
```

Example:

```bash
useradd devops
```

---

# Phase 10: Privilege Configuration

The user was granted administrative privileges.

Ubuntu:

```
devops ALL=(ALL:ALL) NOPASSWD: ALL
```

CentOS:

```
devops ALL=(ALL) NOPASSWD: ALL
```

The goal:

The DevOps user should be able to perform administrative tasks without using root directly.

---

# Current Project Status

## Completed

✅ Infrastructure architecture designed
✅ Vagrant environment created
✅ Five VMs running
✅ Private networking configured
✅ Hostnames configured
✅ Connectivity tested
✅ VM stability issues resolved
✅ Resource allocation configured
✅ DevOps user created on all machines
✅ Sudo privileges configured

---

# Current Task

## Task 1 Remaining:

### SSH key authentication between machines

Target:

```
devops@web01
        |
        |
        +---- devops@app01
        |
        +---- devops@app02
        |
        +---- devops@rmq01
        |
        +---- devops@db01
```

Requirements:

* Generate SSH keys
* Exchange public keys
* Verify passwordless SSH
* Document connections

---

# Lessons Learned So Far

## 1. Infrastructure is a system, not just machines

A server is not only a VM.

It includes:

* Identity
* Networking
* Security
* Resources
* Automation

---

## 2. Errors are part of infrastructure work

Problems encountered:

* SSH failures
* VM boot failures
* Kernel panic
* Vagrant syntax errors
* User permission issues

Each problem improved understanding.

---

## 3. Automation comes after understanding

The project reinforced an important DevOps principle:

> First understand the manual process. Then automate it.

Tools like Vagrant, HostManager, scripts, Docker, and Kubernetes abstract complexity, but understanding the foundation makes those tools meaningful.

---

## 4. Scaling mindset developed

The architecture introduced the idea of scaling individual components:

Example:

```
RabbitMQ overloaded
        |
        ↓
Increase RabbitMQ capacity


Database overloaded
        |
        ↓
Scale database resources
```

Different parts of the system have different bottlenecks.

---

# Next Milestone

Continue from:

```
Task 1:
SSH key-based authentication
```

After that:

1. MariaDB setup
2. RabbitMQ setup
3. Spring Boot deployment
4. Nginx reverse proxy
5. Deployment automation
6. Monitoring
7. Backups
8. Disaster recovery
9. Containerization
10. Kubernetes migration

---

## Current Achievement

The project has moved from:

"Creating VMs"

to:

"Designing and operating infrastructure."

This is the transition from learning DevOps tools to thinking like a DevOps engineer.
