# File              : Makefile
# Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
# Date              : 30.11.2021
# Last Modified Date: 26.02.2022
EMULATOR=~/Library/Android/sdk/emulator/emulator -avd Pixel_3a_API_31_arm64-v8a
# Last Modified By  : Igor V. Sementsov <ig.kuzm@gmail.com>
ADB="~/Library/Android/sdk/platform-tools/adb"

all: build run

build:
	rm -f start_emulator.sh;
	rm -f start_emulator;
	rm -f run_app;
	rm -f install.sh;
	rm -f install;
	./gradlew build	

run:
	echo "#!/bin/bash">start_emulator
	echo "">>start_emulator	
	echo "$(EMULATOR)">>start_emulator	
	chmod +x start_emulator
	echo "#!/bin/bash">install
	echo "" >> install
	echo "if [ \"\`$(ADB) install  app/build/outputs/apk/debug/app-debug.apk|grep 'Install Success'\`\"  !=  \"\" ]" >> install
	echo "then" >> install
	echo "echo 'Application installed!'" >> install
	echo "else" >> install
	echo "open start_emulator" >> install
	echo "sleep 9s" >> install
	echo "$(ADB) install  app/build/outputs/apk/debug/app-debug.apk" >> install
	echo "fi" >> install
	echo "$(ADB) shell am start -n com.example.astroybat/com.example.astroybat.MainActivity" >> install
	chmod +x install
	#echo "$(ADB) shell logcat -v color -v brief output > run.log &" >> run
	echo "#!/bin/bash">run_app
	echo "">>run_app
	echo "./install">>run_app
	echo "" >> run_app
	echo "sleep 4s" >> install
	echo "$(ADB) logcat  -v brief output --pid=\`$(ADB) shell pidof -s kuzm.ig.oldboy\` > run.log &" >> run_app
	echo "open run.log" >> run_app
	echo "echo 'Press any key to stop application'" >> run_app
	echo "read -n1 ans" >> run_app
	echo "$(ADB) shell am force-stop com.example.astroybat" >> run_app
	chmod +x run_app
	./run_app	

clean:
	rm -f run
	./gradlew clean		

.Phony: build run clean
