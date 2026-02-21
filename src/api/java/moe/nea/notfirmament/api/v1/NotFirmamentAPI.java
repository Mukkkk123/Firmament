package moe.nea.notfirmament.api.v1;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Methods you can call to get information about notfirmaments current state.
 */
@ApiStatus.NonExtendable
public abstract class NotFirmamentAPI {
	private static @Nullable NotFirmamentAPI INSTANCE;

	/**
	 * @return the canonical instance of the {@link NotFirmamentAPI}.
	 */
	public static NotFirmamentAPI getInstance() {
		if (INSTANCE != null)
			return INSTANCE;
		try {
			return INSTANCE = (NotFirmamentAPI) Class.forName("moe.nea.notfirmament.impl.v1.NotFirmamentAPIImpl")
				.getField("INSTANCE")
				.get(null);
		} catch (IllegalAccessException | NoSuchFieldException | ClassCastException e) {
			throw new RuntimeException("NotFirmament API implementation class found, but could not load api instance.", e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could not find NotFirmament API, check FabricLoader.getInstance().isModLoaded(\"notfirmament\") first.");
		}
	}

	/**
	 * @return list-view of registered extensions
	 */
	public abstract List<? extends NotFirmamentExtension> getExtensions();

	/**
	 * Obtain a reference to the currently hovered item widget, which may be either in the item list or placed in a UI.
	 * This widget may or may not also be present in the Widgets on the current screen.
	 *
	 * @return the currently hovered notfirmament item widget.
	 */
	public abstract Optional<NotFirmamentItemWidget> getHoveredItemWidget();
}
