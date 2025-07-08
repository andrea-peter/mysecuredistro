inherit extrausers

IMAGE_INSTALL:append = "sudo"
IMAGE_INSTALL:append = " lynis"

EXTRA_USERS_PARAMS = "\
    usermod -L -e 1 root; \
    useradd -G sudo -p '\$1\$2zmmJKJz\$nzopq3SnPdGPZEb9PeAnC0' andrea; \
    "
# Add sudo access to users in the sudo group
update_sudoers(){
    # Uncomment the line adding the sudo group to sudoers
    sed -i 's/^#\s*\(%sudo\s*ALL=(ALL:ALL)\s*ALL\)/\1/'  ${IMAGE_ROOTFS}/etc/sudoers
}


ROOTFS_POSTPROCESS_COMMAND += "update_sudoers; "
