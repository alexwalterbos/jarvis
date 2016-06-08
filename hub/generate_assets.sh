# !/bin/bash
set -e

cd src/main/resources/app/
npm install
npm run build
npm run copy-to-target
