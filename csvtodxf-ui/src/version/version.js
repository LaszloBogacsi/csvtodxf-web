import React, {Component} from 'react'

export default class Version extends Component {
    render() {
        return <div className={this.props.className}>Version: {process.env.REACT_APP_GIT_SHA}</div>
    }
}