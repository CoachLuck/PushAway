# PushAway

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1c2e525f1724406c9a1ccb935ff7e1aa)](https://app.codacy.com/manual/CoachLuck/PushAway?utm_source=github.com&utm_medium=referral&utm_content=CoachLuck/PushAway&utm_campaign=Badge_Grade_Dashboard)

### Commands
- /pushaway
- /pushaway reload
- /pushaway give
- /pushaway give [player]

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
