// Configuration file for festas_builds Minecraft Server Website
// This file is generated at build time from config.template.js
// Environment variables are injected during the Docker build process

window.MC_CONFIG = {
    // Minecraft version - injected from MINECRAFT_VERSION env var
    minecraftVersion: '${MINECRAFT_VERSION}',
    
    // Server software - injected from SERVER_SOFTWARE env var
    serverSoftware: '${SERVER_SOFTWARE}',
    
    // Server address
    serverAddress: 'mc.festas-builds.com',
    
    // Max players
    maxPlayers: 20,
    
    // Öffentliche Quellen für den Netzwerk-Gesamtstatus (oben)
    publicStatusSources: [
        { name: 'mcsrvstat.us', type: 'mcsrvstat', url: 'https://api.mcsrvstat.us/3/' },
        { name: 'mcstatus.io', type: 'mcstatusio', url: 'https://api.mcstatus.io/v2/status/java/' }
    ],

    // Legacy-Feld; bleibt zur Abwärtskompatibilität bestehen.
    statusAPI: 'https://api.mcsrvstat.us/3/',

    // Live player list written by the proxy/plugin and served same-origin
    playersAPI: '/api/players.json',
    
    // External links
    bluemapURL: 'https://survival.festas-builds.com',
    bluemapMiningURL: 'https://mining.festas-builds.com',
    statsURL: 'https://mc-stats.festas-builds.com',
    githubURL: 'https://github.com/Festas/Minecraft-Server',
    
    // Social media links
    social: {
        tiktok: 'https://www.tiktok.com/@festas_builds',
        instagram: 'https://www.instagram.com/festas_builds',
        youtube: 'https://www.youtube.com/@festas',
        twitch: 'https://www.twitch.tv/festas'
    }
};
