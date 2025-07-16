LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# See openembedded-core/meta-skeleton/reciper-skeleton/useradd
inherit useradd

# Which packages will contain user/group creaton
USERADD_PACKAGES += "${PN}"

# Create monitoring user and group
GROUPADD_PARAM:${PN} = "monitoring"
USERADD_PARAM:${PN} = "-s /bin/sh -g monitoring monitoring"

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "myservice.timer"

SRC_URI = " file://myservice.service file://myservice.timer file://log_loadavg.sh"

# ideally this would be in /var/log, but for simplicity its this
LOG_DST = "/home/monitoring/read-load-avg"

FILES:${PN} += "${systemd_unitdir}/system/myservice.service"
FILES:${PN} += "${systemd_unitdir}/system/myservice.timer"
FILES:${PN} += "/usr/bin/myservice.timer"
FILES:${PN} += "${LOG_DST}"

do_install:append() {
    # Install service
    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/myservice.service ${D}/${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/myservice.timer ${D}/${systemd_unitdir}/system
    # Install executable
    install -d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/log_loadavg.sh ${D}/usr/bin
    # Create log destination dir
    install -d -m 700 ${D}/${LOG_DST}
    chown -R monitoring ${D}/${LOG_DST}
    chgrp -R monitoring ${D}/${LOG_DST}
}

# Needed in order to install into /var
VOLATILE_LOG_DIR="no"
