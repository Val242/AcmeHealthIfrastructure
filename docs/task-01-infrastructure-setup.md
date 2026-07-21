Task 1: Network and SSH Setup

- Created 5 VMs using Vagrant:
  - web01
  - app01
  - app02
  - rmq01
  - db01

- Configured private network:
  192.168.56.0/24

- Enabled hostname resolution using vagrant-hostmanager

- Created devops user on all machines

- Added sudo privileges:
  devops ALL=(ALL) NOPASSWD:ALL

- Generated SSH keys

- Distributed public keys using copyKeys.sh

- Verified passwordless SSH between machines