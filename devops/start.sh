#!/usr/bin/env bash

work_path=$(cd `dirname $0`; pwd)

cd ${work_path} && docker-compose up -d

