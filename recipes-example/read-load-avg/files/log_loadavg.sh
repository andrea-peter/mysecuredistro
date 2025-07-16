#!/bin/sh

OUT_DIR=/home/monitoring/read-load-avg

tail -n30 /proc/loadavg >>"${OUT_DIR}"/load-avg.log
