require ${@bb.utils.contains('DISTRO_FEATURES', 'security', '${BPN}-hardening.inc', '', d)}
