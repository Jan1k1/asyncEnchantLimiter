# AsyncEnchantLimiter
### The #1 Optimized, Folia-Supported Enchantment Limit Plugin for Minecraft 1.20+

![Banner](https://cdn.modrinth.com/data/cached_images/45c7bb4b6dcc75ec701cea28e4c1961328ce0fe3.png)

**AsyncEnchantLimiter** is the ultimate solution for server owners who need strict, high-performance control over enchantment levels. Built from the ground up for **Folia** and modern Paper servers, it ensures zero lag while preventing over-enchanted items from ruining your economy or PvP balance.

---

## Premium Interface

![GUI Screenshot](gui_screenshot_url)
*Manage every enchantment limit instantly through our intuitive, paginated GUI.*

![Chat Alert](chat_alert_url)
*Clean, hex-colored notifications keep players informed without cluttering the chat.*

![Console Branding](console_branding_url)
*Professional "Premium" branding with custom ASCII art on startup.*

---

## Why AsyncEnchantLimiter?

*   **Folia Supported**: Native threading and region scheduler support for multi-threaded servers.
*   **Zero Bypasses**: Strict checks on Hold, Click, Equip, Trade, Anvil, Fishing, and Loot Gen.
*   **High Performance**: All IO and heavy checks are handled asynchronously to keep TPS at 20.0.
*   **Villager Control**: Auto-fixes illegal trades from villagers instantly.
*   **Branded Experience**: A clean, professional aesthetic with hex-colored messaging and custom fonts.

## Key Features

*   **Global Limits**: Set max levels for any enchantment (e.g., Sharpness 5, Protection 4).
*   **Anvil Blocking**: Prevents combining items to exceed limits (returns empty result).
*   **Loot Control**: Limits items from fishing, mob drops, and dungeon chests.
*   **Villager Trade Fixer**: Automatically downgrades over-enchanted trades from villagers.
*   **Silent Mode**: Option to silently downgrade items without notifying the player.
*   **In-Game Editor**: Modify limits on the fly with `/el gui` or `/el admin`.

## Configuration

Simple, powerful, and clean. Everything is separated into `config.yml` and `messages.yml`.

```yaml
settings:
  enforce-on-hold: true    # Checks when swapping hotbar slots
  fix-villager-trades: true # Auto-corrects villagers
  notify-player: true      # Alert players on downgrade
  silent-mode: false       # Disable alerts for stealth enforcement

limits:
  sharpness: 5
  efficiency: 5
  protection: 4
```

## Installation

1.  Download the **AsyncEnchantLimiter** JAR.
2.  Drop it into your `plugins` folder.
3.  Restart your server.
4.  Configure your limits via `/el gui` or the `config.yml`.

## Permissions

*   `enchantlimiter.admin` - Access to the configuration GUI and reload command.
*   `enchantlimiter.bypass` - Permission to bypass all enchantment restrictions.
