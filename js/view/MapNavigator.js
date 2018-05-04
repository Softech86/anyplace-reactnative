import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View
} from 'react-native';
import { MapView } from 'react-native-amap3d'


const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' +
    'Cmd+D or shake for dev menu',
  android: 'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

export default class App extends Component {
  constructor (props) {
    super(props)
    this.state = {
      mounted: false,
      coordinate: {
        latitude: 0,
        longitude: 0,
      }
    }
  }

  componentDidMount () {
    console.log('mounted')
    this.setState({ mounted: true })
  }

  componentWillUnmount () {
    this.setState({ mounted: false })
  }

  render() {
    return (
      <View style={styles.container}>
        <MapView
          style={StyleSheet.absoluteFill}
          locationEnabled
          coordinate={this.state.coordinate}

          onLocation={
            ({ nativeEvent }) => {
              console.log(`Location: ${nativeEvent.latitude}, ${nativeEvent.longitude}`)
              this.setState({coordinate: nativeEvent})
            }
          }
        >
          <MapView.Marker
            title='这是一个可拖拽的标记'
            coordinate={this.state.coordinate}
          />
        </MapView>

        <Text style={styles.welcome}>
          Welcome to React Native~
        </Text>
        <Text style={styles.instructions}>
          To get started, edit App.js
        </Text>
        <Text style={styles.instructions}>
          {instructions}
        </Text>

      </View>
    );
  }
}

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
