// https://skirtlesden.com/articles/html-and-extjs-components
var dataStore = Ext.create('Ext.data.Store', {
   fields: [
       {name: 'id', mapping: 'id' },
       {name: 'day', mapping: 'day' },
       {name: 'cases', mapping: 'cases' }]
});

var win = Ext.create('Ext.chart.CartesianChart', {
    id: 'graph',
    layout: 'fit',
    autoWidth: true,
    height: 400,
    insetPadding: 40,
    store: dataStore,
    axes: [{
        type: 'numeric',
        position: 'left',
        fields: ['cases'],
        title: {
            text: 'Cases',
            fontSize: 15
        },
        grid: true,
        minimum: 0
    }, {
        type: 'category',
        position: 'bottom',
        fields: ['day'],
        title: {
            text: 'day',
            fontSize: 15
        }
    }],
    series: [{
        type: 'line',
        style: {
            stroke: '#30BDA7',
            lineWidth: 2
        },
        xField: 'day',
        yField: 'cases',
        marker: {
            type: 'path',
            path: ['M', - 4, 0, 0, 4, 4, 0, 0, - 4, 'Z'],
            stroke: '#30BDA7',
            lineWidth: 2,
            fill: 'white'
        }
    }, {
        type: 'line',
        fill: true,
        style: {
            fill: '#96D4C6',
            fillOpacity: .6,
            stroke: '#0A3F50',
            strokeOpacity: .6,
        },
        xField: 'day',
        yField: 'cases',
        marker: {
            type: 'circle',
            radius: 4,
            lineWidth: 2,
            fill: 'white'
        }
    }]
});
var list = new Ext.Component({
autoEl: 'ul',
    data: ['Source', 'Source', 'Source', 'Source', 'Source'],

    tpl: [
        '<tpl for=".">',
            '<li>{.}</li>',
        '</tpl>'
    ]
});

Ext.onReady(function() {
    Ext.create('Ext.container.Container', {
        renderTo: 'Graph',
        items: win,
    });
    Ext.create('Ext.container.Container', {
        renderTo: 'SourceList',
        items: list,
    });
});