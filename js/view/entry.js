import React from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View
} from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { TabNavigator, TabBarBottom } from 'react-navigation';

const options = {
  navigationOptions: ({ navigation }) => ({
    tabBarIcon: ({ focused, tintColor }) => {
      const { routeName } = navigation.state;
      let iconName;
      console.log(routeName)
      if (routeName === 'Map') {
        iconName = `map${focused ? '' : '-o'}`;
      } else if (routeName === 'Profile') {
        iconName = `${focused ? 'folder-open' : 'folder-o'}`;
      }

      // You can return any component that you like here! We usually use an
      // icon component from react-native-vector-icons
      return <Icon name={iconName} size={25} color={tintColor} />;
    },
  }),
  tabBarComponent: TabBarBottom,
  tabBarPosition: 'bottom',
  tabBarOptions: {
    activeTintColor: 'tomato',
    inactiveTintColor: 'gray',
  },
  animationEnabled: false,
  swipeEnabled: false,
}


import MapNavigator from './MapNavigator.js';
import Profile from './Profile.js'

const Nav = TabNavigator(
  {
    Profile: {screen: Profile},
    Map: {screen: MapNavigator},
  }, options);

export default Nav
