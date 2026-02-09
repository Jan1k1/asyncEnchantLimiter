# AsyncEnchantLimiter | Best Optimized Enchantment Limiter
### Built for Folia & modern Paper servers - No more lag from over-enchanted items.

![banner](https://cdn.modrinth.com/data/cached_images/45c7bb4b6dcc75ec701cea28e4c1961328ce0fe3.png)

**AsyncEnchantLimiter** is a simple but powerful plugin that stops players from using items with enchantments above your set limits. It was built from scratch to be the fastest option out there, specifically designed for **Folia** and heavy Paper servers where other plugins might cause TPS drops.

---

## Features you'll love

### Easy to use GUI
Manage every enchantment limit right in the game. It's paginated, fast, and stays in sync as you change things.
![gui](https://cdn.modrinth.com/data/cached_images/f0e8f65a867eda04376a152209b800d3cf912483.png)

### Clean Notifications
No ugly spam. Players get a clean, themed message when an item gets downgraded so they know exactly what happened.
![blocked](https://cdn.modrinth.com/data/cached_images/922034be217dd43362e772c98b455c4ec21ddcb1.png)

---

## Why choose this plugin?

*   **Native Folia Support**: Works perfectly with Folia's region scheduler. No crashes or thread issues.
*   **Truly Asynchronous**: We handle all the heavy lifting (like saving files and scanning items) off the main thread so your server stays at 20.0 TPS.
*   **Total Control**: Checks items when players hold them, click them, equip them, or trade them. Nothing slips through.
*   **Villager Fixer**: Tired of villagers trading Sharpness 10? This plugin fixes those trades automatically the second they appear.
*   **Premium Look**: Uses a nice pink/gray theme with custom small-caps text that looks great in any chat.

## What it can do

*   **Set Max Levels**: Easily cap any enchantment (like Sharpness 5, Prot 4, or whatever you want).
*   **Anvil Blocking**: Stops players from combining items to go past your limits.
*   **Loot Cleaning**: Automatically checks and fixes items from fishing, mob drops, and dungeon chests.
*   **Admin Tools**: Change everything in-game with `/el gui` or quickly reload with `/el reload`.
*   **Bypass Permissions**: Give certain ranks (like staff) the ability to use over-enchanted items if needed.

## Commands & Permissions

| Command | Alias | Description | Permission |
| :--- | :--- | :--- | :--- |
| `/enchantlimiter` | `/el` | Open the main menu. | `enchantlimiter.admin` |
| `/el gui` | | Open the limit editor. | `enchantlimiter.admin` |
| `/el reload` | | Reload all configs. | `enchantlimiter.admin` |

## Simple Setup
Just drop the jar in your plugins folder and you're good to go. The config is super clean and easy to read:

```yaml
settings:
  enforce-on-hold: true    # Check items as players hold them
  fix-villager-trades: true # Auto-fix broken villager trades
  notify-player: true      # Let players know when an item is fixed
  silent-mode: false       # Set to true to fix items without any messages

limits:
  sharpness: 5
  efficiency: 5
  protection: 4
```

---
**Found a bug?** If you run into any issues or have a suggestion, please let us know on [Discord](https://discord.gg/38Ebj42e).
**Keywords**: enchantment, limit, performance, optimized, folia, paper, villager, fix, enforce
