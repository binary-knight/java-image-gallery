#!/bin/bash
#su ec2-user
curl -s "https://get.sdkman.io" | bash
source "/root/.sdkman/bin/sdkman-init.sh"
sdk install gradle 6.5
source /root/.bashrc
