echo grant { permission java.security.AllPermission; }; > .java.policy
copy .java.policy "%HOMEDRIVE%%HOMEPATH%"
copy EZTW32.dll "%windir%\system32"