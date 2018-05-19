import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  Button,
  FlatList,
  View
} from 'react-native';
import { NativeModules, DeviceEventEmitter } from 'react-native';

export default class SettingsScreen extends Component {

  constructor (props) {
    super(props)
    this.state = {
      mounted: false,
      wifi: [],
      battery: 0,
      signal: 0,
      location: 'no'
    }
  }

  componentDidMount () {
    DeviceEventEmitter.addListener('signalStrengthsChanged', signal => {
      console.log('signal', signal)
      this.setState({signal})
    })
    NativeModules.SignalManager.startListening()
    DeviceEventEmitter.addListener('locationChanged', location => {
      this.setState({location})
    })
    NativeModules.LocationManager.startListening()
    const getStatus = async () => {
      const wifi = await NativeModules.WifiManager.getScanResults()
      const battery = await NativeModules.BatteryManager.getBatteryStatus()
      this.setState({ wifi, battery })
    }
    getStatus()
    setInterval(getStatus, 2000)
  }

  render () {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text>Battery: { JSON.stringify(this.state.battery) }</Text>
        <Text>Signal: { JSON.stringify(this.state.signal) }</Text>
        <Text>Location: { JSON.stringify(this.state.location) }</Text>
        <Text>Scanned Wifi Number: { this.state.wifi.length }</Text>
        <FlatList
          data={ this.state.wifi }
          renderItem={ ({ item }) => <Text>{ JSON.stringify(item) }</Text> }
        />
      </View>
    );
  }
}

  // {
  //   this.state.wifi.map(x => <Text>{ x }</Text>)
  // }

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F500FF',
  },
  map: {
    flex: 1,
    backgroundColor: '#00FCFF',
    width: 300,
    height: 100
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },

  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
