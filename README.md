<div align="center">

  <a href="https://github.com/Norbit4/TreeCuter/" target="_blank" rel="noreferrer"> 
  <img src="https://github.com/Norbit4/BetterCommands/assets/46154743/200fbfa2-849c-453a-be54-0593cd22e1b4" width=950" alt="logo"/></a>

  [![Spigot](https://img.shields.io/badge/Download-Spigot-gold.svg)](https://www.spigotmc.org/resources/%E2%9C%A8bettercommands%E2%9C%A8-easily-create-commands-%E2%9C%85.113189/) 
  [![builtbybit](https://img.shields.io/badge/Download-BuiltByBit-blue.svg)](https://builtbybit.com/resources/bettercommands-easily-create-commands.33345/)                                                                                                                   
  [![License: GPL v3](https://img.shields.io/badge/license-GPLv3-orange.svg)](https://github.com/Norbit4/TreeCuter/blob/master/LICENSE)                                                                                                                          
                                                                         
</div> 

                                                                                                                                                                                                                                      

#  
**Commands**

    /bettecommands reload - reload plugin config.

  ⚠️ When you add new command or change name existing command, you need to restart server. Bukkit server don't support add new commands without restart server :c   
  
#                                                                                                                          
                                                                                                                            
***Config***
```yml
#---------------------------------------#
#                [Help]                 #
#---------------------------------------#

# 1.Reload config:
#  To reload config use /bc reload, or /bettercommands reload command.

#  [!] When you add new command or change name existing command, you need to restart server!
#  Bukkit server don't support add new commands without restart server :c

# 2.Actions:
#   - replace:
#      replace command to another command
#      ex. /core -> /essentials, when player use /core reload it will replace to /essentials reload

#   - text:
#      send message to player who use command

#   - broadcast:
#      broadcast message to all players on server

#   - server_command:
#      execute command as console

#   - player_command:
#      execute command as player

# 3.Sub-commands:
#   - You can create sub-commands for main command
#   - To use sub-commands, use /main-command sub-command
#   - enable/disable tab completer for sub-commands

# 4.Placeholders:
#   - {PLAYER} - player name who use command

#  For other placeholders, use PlaceholderAPI plugin:
#   - https://www.spigotmc.org/resources/placeholderapi.6245/

# 4.Hex colors:
#   - To use HEX color, use &#HEXCODE (ex. &#DBC7FF)

# hex codes: https://htmlcolorcodes.com/
#---------------------------------------#
#               [Config]                #
#---------------------------------------#

blocked:
  commands:
    - 'pl'
    - 'plugins'
    - 'help'
    - 'version'
    - '?'
  perm: 'bc.*' # <- permission to use blocked commands
  message: '&cYou cannot use this command!' # <- default message when player don't have permission

commands:
  core: # <- command name (without /)
    perm: 'core.admin' # <- when player don't have permission, it will send message default message
    actions:
      action-1:
        type: 'replace' # <- replace, message, broadcast
        action:
          - 'essentials' # <- command to replace, when player use /core, it will replace to /essentials
  info:
    perm: 'info.admin' # <- permission to use this command
    perm-message: '&cYou cannot use this command!' # <- message when player don't have permission
    completer: true
    #multi actions
    actions:
      action-1:
        type: 'text'
        action:
          - '&#DBC7FFSended to all players!!' # <- to use HEX color, use &#HEXCODE
      action-2:
        type: 'broadcast'
        action:
          - '&aSended from {PLAYER}!'
          - '&aHI ALL!'
  diamonds:
    actions:
      action-1:
        type: 'text'
        action:
          - '&#DBC7FFDiamonds!'
      action-2:
        type: 'server_command' # <- execute command as console
        action:
          - 'give {PLAYER} minecraft:diamond 1' # (without /)
  sop:
    completer: true # <- enable tab completer
    actions:
      action-1:
        type: 'text'
        action:
          - '&aUsage: /sop <op/deop>'
    sub-commands: # <- sub-commands
      op: # <- sub-command name, use /sop op
        actions:
          action-1:
            type: 'server_command'
            action:
              - 'op {PLAYER}'
      deop: # <- sub-command name, use /sop deop
        actions:
          action-1:
            type: 'server_command'
            action:
              - 'deop {PLAYER}'
```                                                                                                                    
## Links
 [![spigot](https://img.shields.io/badge/Download-Spigot-gold.svg)](https://www.spigotmc.org/resources/%E2%9C%A8bettercommands%E2%9C%A8-easily-create-commands-%E2%9C%85.113189/)    
 [![builtbybit](https://img.shields.io/badge/Download-BuiltByBit-blue.svg)](https://builtbybit.com/resources/bettercommands-easily-create-commands.33345/)    
