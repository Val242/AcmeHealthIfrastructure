Vagrant.configure("2") do |config|

  config.hostmanager.enabled = true
  config.hostmanager.manage_host = true
  config.hostmanager.manage_guest = true

  config.vm.define "web01" do |web01|
    web01.vm.box = "ubuntu/jammy64"
    web01.vm.hostname = "web01"
    web01.vm.network "private_network", ip: "192.168.56.20"

    web01.vm.provider "virtualbox" do |vb|
      vb.gui = true
      vb.memory = "1024"
    end
  end


  config.vm.define "app01" do |app01|
    app01.vm.box = "eurolinux-vagrant/centos-stream-9"
    app01.vm.hostname = "app01"
    app01.vm.network "private_network", ip: "192.168.56.21"

    app01.vm.provider "virtualbox" do |vb|
      vb.memory = "800"
    end
  end


  config.vm.define "app02" do |app02|
    app02.vm.box = "eurolinux-vagrant/centos-stream-9"
    app02.vm.hostname = "app02"
    app02.vm.network "private_network", ip: "192.168.56.22"

    app02.vm.provider "virtualbox" do |vb|
      vb.memory = "800"
    end
  end


  config.vm.define "rmq01" do |rmq01|
    rmq01.vm.box = "eurolinux-vagrant/centos-stream-9"
    rmq01.vm.hostname = "rmq01"
    rmq01.vm.network "private_network", ip: "192.168.56.23"

    rmq01.vm.provider "virtualbox" do |vb|
      vb.memory = "600"
    end
  end


  config.vm.define "db01" do |db01|
    db01.vm.box = "eurolinux-vagrant/centos-stream-9"
    db01.vm.hostname = "db01"
    db01.vm.network "private_network", ip: "192.168.56.24"

    db01.vm.provider "virtualbox" do |vb|
      vb.memory = "600"
    end
  end

end