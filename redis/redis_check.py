#!/usr/bin/python
#-*- coding: UTF-8 -*-
#coding=utf-8
import redis
import time
import os
import logging
logging.basicConfig(level = logging.INFO,format = '[*] %(asctime)s - %(levelname)s : %(message)s')
logger = logging.getLogger(__name__)

host = "127.0.0.1"
port = 6379
password=""

if __name__ == '__main__':
    while True:
        r = redis.Redis(host=host, port=6379, password=password, socket_connect_timeout=1)
        try:
            r.ping()
            logging.info(" redis %s:%s running !"%(host,str(port),))
        except Exception as e:
            logging.error("redis %s:%s stop, rebooting !"%(host,str(port),))
            p = os.system("systemctl start redis")
            logging.error("redis %s:%s stop, reboot ok !"%(host,str(port),))
        finally:
            r.close()
        time.sleep(2)