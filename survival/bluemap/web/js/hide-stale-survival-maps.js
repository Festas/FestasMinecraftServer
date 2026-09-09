(() => {
  const hiddenMapIds = new Set(["town", "freebuild", "world_nether", "world_the_end"]);
  const fallbackMapId = "world";
  const bluemap = window.bluemap;

  if (!bluemap) return;

  const pruneHiddenMaps = async () => {
    if (Array.isArray(bluemap.settings?.maps)) {
      bluemap.settings.maps = bluemap.settings.maps.filter((id) => !hiddenMapIds.has(id));
    }

    if (Array.isArray(bluemap.maps)) {
      bluemap.maps = bluemap.maps.filter((map) => !hiddenMapIds.has(map?.data?.id));
    }

    if (bluemap.mapsMap instanceof Map) {
      for (const id of hiddenMapIds) {
        bluemap.mapsMap.delete(id);
      }
    }

    if (Array.isArray(bluemap.appState?.maps)) {
      for (let i = bluemap.appState.maps.length - 1; i >= 0; i -= 1) {
        if (hiddenMapIds.has(bluemap.appState.maps[i]?.id)) {
          bluemap.appState.maps.splice(i, 1);
        }
      }
    }

    const currentMapId = bluemap.mapViewer?.map?.data?.id;
    if (currentMapId && hiddenMapIds.has(currentMapId) && bluemap.mapsMap?.has(fallbackMapId)) {
      try {
        await bluemap.switchMap(fallbackMapId, true);
      } catch (error) {
        console.error("Failed to switch away from a hidden stale BlueMap map.", error);
      }
    }
  };

  void pruneHiddenMaps();
})();
