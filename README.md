# PushAway ![Java CI with Maven](https://github.com/CoachLuck/PushAway/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master) [![Codacy Badge](https://app.codacy.com/project/badge/Grade/dbd47d714e5141d0b20f5275c26b16d7)](https://www.codacy.com/manual/CoachLuck/PushAway?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=CoachLuck/PushAway&amp;utm_campaign=Badge_Grade)

### Commands
- /pushaway - shows the help
- /pushaway reload - reloads the plugin
- /pushaway give - gives you the Push Away item
- /pushaway give [player] - gives the player the pushaway item

### Permissions
```
permissions:
  pushaway.*:
    children:
      pushaway.reload: true
      pushaway.use: true
      pushaway.givewand: true
      pushaway.givewand.others: true
      pushaway.bypass-cooldown: true
  pushaway.use:
    description: Allow the player to use the wand.
    default: op
  pushaway.givewand:
    description: Allow the player to give themselves a wand.
    default: op
  pushaway.givewand.others:
    description: Allow the player to give anyone a wand.
    default: op
    children:
      pushaway.givewand: true
  pushaway.reload:
    description: Reload the configuration file.
    default: op
  pushaway.bypass-cooldown:
    description: Bypass the cooldown to use the push away item.
    default: op
```

### Configuration
```
# Range is how close players have to be to get launched
# Launch-X is how far horizontally they get launched
# Launch-Y is how far vertically they get launched
Push-Away-Range: 3
Push-Away-Launch-X: .7
Push-Away-Launch-Y: .5
Usage-Cooldown: 5
Wand:
  Material: "RECORD_3"
  Name: "&a&lPushAway Wand"
  Lore:
    - "&7Right click to &epush &7players away!"
    - "&7Works up to &e%range% blocks"
Sound:
  On-Push:
    Enabled: true
    Sound: "BLOCK_ANVIL_PLACE"
    Volume: 1.0
    Pitch: 1.0
  When-Pushed:
    Enabled: true
    Sound: "ENTITY_BLAZE_SHOOT"
    Volume: 1.0
    Pitch: 1.0
Messages:
  Permission: "&cYou do not have permission to use this command!"
  Given-Wand: "&7You have been given a &a&lPushAway Wand&7!"
  Give-Other-Wand: "&7You have given &e%player% &7a &a&lPushAway Wand&7!"
  Pushed-Players: "&7You have &e&lLAUNCHED &a%num% &7players away from you!"
  Pushed-By-Player: "&7You have been &epushed away &7by &c%player%&7!"
  No-Players-Nearby: "&7There are no players nearby!"
  Cooldown: "&cYou are on cooldown for &e%time% &cmore seconds!"
```
