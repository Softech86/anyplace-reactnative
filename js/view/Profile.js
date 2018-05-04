import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  Button,
  FlatList,
  View
} from 'react-native';
import { NativeModules } from 'react-native';

export default class SettingsScreen extends Component {

  constructor (props) {
    super(props)
    this.state = {
      mounted: false,
      wifi: []
    }
  }

  async componentDidMount () {
    const wifi = await NativeModules.WifiManager.getScanResults()
    this.setState({ wifi })
  }

  render () {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>

        <FlatList
          data={ this.state.wifi }
          renderItem={ ({ item }) => <Text>{ item }</Text> }
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
