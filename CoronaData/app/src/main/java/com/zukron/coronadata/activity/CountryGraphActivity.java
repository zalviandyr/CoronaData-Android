package com.zukron.coronadata.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.VolleyError;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.zukron.coronadata.R;
import com.zukron.coronadata.model.Country;
import com.zukron.coronadata.networking.APICountryDate;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class CountryGraphActivity extends AppCompatActivity implements APICountryDate.OnResponse {
    private String country;
    private AnyChartView anyChartView;
    private APICountryDate apiCountryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_graph_acitivity);

        apiCountryDate = new APICountryDate(this, this);

        anyChartView = findViewById(R.id.anv_graph_global);
        anyChartView.setProgressBar(findViewById(R.id.pb_graph_global));

        country = getIntent().getStringExtra("country");

        getData();
    }

    private void getData() {
        apiCountryDate.getCountryByDateInterval(country, 10);
    }

    private void setGraph(List<DataEntry> dataEntries) {
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Grafik Negara " + country);

        cartesian.yAxis(0).title("Angka Kasus Tahun 2020");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        Set set = Set.instantiate();
        set.data(dataEntries);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Dikonfirmasi");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Sembuh");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("Meninggal");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }

    @Override
    public void countriesResponse(ArrayList<Country> countries) {
        List<DataEntry> dataEntries = new ArrayList<>();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd LLLL yyyy");

        for (Country country : countries){
            String dateStr = country.getLastUpdate().format(date);
            int confirmed = country.getConfirmed();
            int recovered = country.getRecovered();
            int deaths = country.getDeaths();

            dataEntries.add(new CustomDataEntry(dateStr, confirmed, recovered, deaths));
        }

        setGraph(dataEntries);
    }

    @Override
    public void errorResponse(VolleyError error) {

    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}