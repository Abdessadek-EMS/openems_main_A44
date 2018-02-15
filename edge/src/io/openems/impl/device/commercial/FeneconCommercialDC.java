/*******************************************************************************
 * OpenEMS - Open Source Energy Management System
 * Copyright (c) 2016, 2017 FENECON GmbH and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *   FENECON GmbH - initial API and implementation and initial documentation
 *******************************************************************************/
package io.openems.impl.device.commercial;

import java.util.HashSet;
import java.util.Set;

import io.openems.api.bridge.Bridge;
import io.openems.api.channel.ConfigChannel;
import io.openems.api.device.nature.DeviceNature;
import io.openems.api.doc.ChannelInfo;
import io.openems.api.doc.ThingInfo;
import io.openems.api.exception.OpenemsException;
import io.openems.impl.protocol.modbus.ModbusDevice;

@ThingInfo(title = "FENECON Commercial DC/Hybrid")
public class FeneconCommercialDC extends ModbusDevice {

	/*
	 * Constructors
	 */
	public FeneconCommercialDC(Bridge parent) throws OpenemsException {
		super(parent);
	}

	/*
	 * Config
	 */
	@ChannelInfo(title = "Ess", description = "Sets the Ess nature.", type = FeneconCommercialEss.class)
	public final ConfigChannel<FeneconCommercialEss> ess = new ConfigChannel<FeneconCommercialEss>("ess", this).addChangeListener(this);

	@ChannelInfo(title = "Charger", description = "Sets the inverter nature.", type = FeneconCommercialCharger.class)
	public final ConfigChannel<FeneconCommercialCharger> charger = new ConfigChannel<FeneconCommercialCharger>("charger", this).addChangeListener(this);

	/*
	 * Methods
	 */
	@Override
	public String toString() {
		return "FeneconCommercialDC [ess=" + ess + ", getThingId()=" + id() + "]";
	}

	@Override
	protected Set<DeviceNature> getDeviceNatures() {
		Set<DeviceNature> natures = new HashSet<>();
		if (ess.valueOptional().isPresent()) {
			natures.add(ess.valueOptional().get());
		}
		if (charger.valueOptional().isPresent()) {
			natures.add(charger.valueOptional().get());
		}
		return natures;
	}
}
