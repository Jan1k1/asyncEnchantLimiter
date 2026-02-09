# AsyncEnchantLimiter
### The #1 Optimized, Folia-Supported Enchantment Limit Plugin for Minecraft 1.20+

![Banner Placeholder](INSERT_BANNER_HERE)

**AsyncEnchantLimiter** is the ultimate solution for server owners who need strict, high-performance control over enchantment levels. Built from the ground up for **Folia** and modern Paper servers, it ensures zero lag while preventing over-enchanted items from ruining your economy or PvP balance.

---

## ⚡ Why AsyncEnchantLimiter?

*   **Folia Supported**: Native threading and region scheduler support.
*   **Zero Bypasses**: Strict checks on Hold, Click, Equip, Trade, Anvil, Fishing, and Loot Gen.
*   **High Performance**: All IO and heavy checks are handled asynchronously.
*   **Villager Control**: Auto-fixes illegal trades from villagers instantly.
*   **Branded Experience**: "No-AI" vibe with sleek hex-color messaging and small caps fonts.

## 📸 Screenshots

![GUI Screenshot](INSERT_GUI_SCREENSHOT_HERE)
*Easily manage limits via in-game GUI*

![Chat Alert](INSERT_CHAT_SCREENSHOT_HERE)
*Sleek alerts when players try to use illegal items*

## 🛠️ Features

*   **Global Limits**: Set max levels for any enchantment (e.g., Sharpness 5, Protection 4).
*   **Anvil Blocking**: Prevents combining items to exceed limits (returns empty result).
*   **Loot Control**: limits items from fishing, mob drops, and dungeon chests.
*   **Villager Trade Fixer**: Automatically downgrades over-enchanted trades.
*   **Silent Mode**: Option to silently downgrade items without spamming chat.
*   **GUI Editor**: Edit limits in-game with `/el gui`.
*   **Async Updates**: Checks for updates without freezing the server.

## 🔧 Configuration

Simple, powerful, and clean.

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

## 📥 Installation

1.  Download the JAR.
2.  Drop it into your `plugins` folder.
3.  Restart your server.
4.  (Optional) Configure limits via `/el gui` or `config.yml`.

## 🔒 Permissions

*   `enchantlimiter.admin` - Access to `/el gui` and `/el reload`.
*   `enchantlimiter.bypass` - Bypass all checks and limits.
