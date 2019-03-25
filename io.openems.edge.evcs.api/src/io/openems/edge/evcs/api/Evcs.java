package io.openems.edge.evcs.api;

import org.osgi.annotation.versioning.ProviderType;

import io.openems.common.types.OpenemsType;
import io.openems.edge.common.channel.AccessMode;
import io.openems.edge.common.channel.Channel;
import io.openems.edge.common.channel.Doc;
import io.openems.edge.common.channel.Unit;
import io.openems.edge.common.channel.WriteChannel;
import io.openems.edge.common.component.OpenemsComponent;
import io.openems.edge.common.modbusslave.ModbusSlaveNatureTable;
import io.openems.edge.common.modbusslave.ModbusType;

@ProviderType
public interface Evcs extends OpenemsComponent {

	public enum ChannelId implements io.openems.edge.common.channel.ChannelId {

		/**
		 * Charge Power.
		 * 
		 * <ul>
		 * <li>Interface: Evcs
		 * <li>Readable
		 * <li>Type: Integer
		 * <li>Unit: W
		 * </ul>
		 */
		CHARGE_POWER(Doc.of(OpenemsType.INTEGER) //
				.unit(Unit.WATT) //
				.accessMode(AccessMode.READ_ONLY)), //
		
		
		/**
		 * Minimum Power .
		 * 
		 * <ul>
		 * <li>Interface: Evcs
		 * <li>Writable
		 * <li>Type: Integer
		 * <li>Unit: W
		 * </ul>
		 */
		 MINIMUM_POWER(Doc.of(OpenemsType.INTEGER) //
				.unit(Unit.WATT) //
				.accessMode(AccessMode.READ_WRITE)),
		
		
		/**
		 * Maximum Power .
		 * 
		 * <ul>
		 * <li>Interface: Evcs
		 * <li>Writable
		 * <li>Type: Integer
		 * <li>Unit: W
		 * </ul>
		 */
		 MAXIMUM_POWER(Doc.of(OpenemsType.INTEGER) //
					.unit(Unit.WATT) //
					.accessMode(AccessMode.READ_WRITE)),
		
		/**
		 * Maximum Power valid by the Hardware.
		 * 
		 * <ul>
		 * <li>Interface: Evcs
		 * <li>Readable
		 * <li>Type: Integer
		 * <li>Unit: W
		 * </ul>
		 */
		HARDWARE_POWER_LIMIT(Doc.of(OpenemsType.INTEGER) //
				.unit(Unit.WATT) //
				.accessMode(AccessMode.READ_ONLY) //
				.text("Highest possible charging power of the charging connection. "
						+ "Contains device maximum, DIP-switch setting, cable coding and temperature reduction.")),
		/**
		 * Set Charge Power.
		 * 
		 * <ul>
		 * <li>Interface: Evcs
		 * <li>Writable
		 * <li>Type: Integer
		 * <li>Unit: W
		 * </ul>
		 */
		SET_CHARGE_POWER(Doc.of(OpenemsType.INTEGER) //
				.unit(Unit.WATT) //
				.accessMode(AccessMode.READ_WRITE)),
		/**
		 * Set Display Text.
		 * 
		 * <ul>
		 * <li>Interface: Evcs
		 * <li>Writable
		 * <li>Type: String
		 * </ul>
		 */
		SET_DISPLAY_TEXT(Doc.of(OpenemsType.STRING) //
				.accessMode(AccessMode.READ_WRITE));

		private final Doc doc;

		private ChannelId(Doc doc) {
			this.doc = doc;
		}

		@Override
		public Doc doc() {
			return this.doc;
		}
	}
	
	
	public static ModbusSlaveNatureTable getModbusSlaveNatureTable() {
		return ModbusSlaveNatureTable.of(Evcs.class, 100) //
				.channel(0, ChannelId.CHARGE_POWER, ModbusType.UINT16) //
				.channel(1, ChannelId.HARDWARE_POWER_LIMIT, ModbusType.UINT16) //
				.channel(2, ChannelId.SET_CHARGE_POWER, ModbusType.UINT16)
				.channel(3, ChannelId.SET_DISPLAY_TEXT, ModbusType.STRING16)
				.build();
	}


	/**
	 * Gets the Charge Power in [W].
	 * 
	 * @return the Channel
	 */
	public default Channel<Integer> getChargePower() {
		return this.channel(ChannelId.CHARGE_POWER);
	}

	/**
	 * Sets the allowed maximum charge power of the EVCS in [W].
	 * 
	 * <p>
	 * Actual charge power depends on
	 * <ul>
	 * <li>whether the electric vehicle is connected at all and ready for charging
	 * <li>limitation of electric vehicle
	 * <li>limitation of power line
	 * <li>...
	 * </ul>
	 * 
	 * @return the WriteChannel
	 */
	public default WriteChannel<Integer> setChargePower() {
		return this.channel(ChannelId.SET_CHARGE_POWER);
	}

	/**
	 * Sets a Text that is shown at the display of the EVCS. Be aware that the EVCS
	 * might not have a display or the text might be restricted.
	 * 
	 * @return the WriteChannel
	 */
	public default WriteChannel<String> setDisplayText() {
		return this.channel(ChannelId.SET_DISPLAY_TEXT);
	}
}
